package kr.tjoeun.numberbaseballgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.tjoeun.numberbaseballgame.adapters.ChatAdapter
import kr.tjoeun.numberbaseballgame.datas.Chat

class MainActivity : BaseActivity() {
//컴퓨터 출제 질문 숫자담을 배열
    val cpuNumList = ArrayList<Int>()
//대화내용 ArrayList
    val chatList = ArrayList<Chat>()
    lateinit var mChatAdapter : ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        inputBtn.setOnClickListener {
//사용자 입력받은 변수
            val inputNum = numberEdt.text.toString()

//입력값이 세자리가 아닌 경우 오류
            if (inputNum.length != 3)
            {
                Toast.makeText(mContext, "세자리 숫자만 입력 가능합니다." , Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }


//입력자가 입력한 값 ME 로 채팅내역 ListView 표시
            chatList.add(Chat("ME",inputNum))
//입력내용 바로 화면 반영되도록
            mChatAdapter.notifyDataSetChanged()
            
//입력완료 후 입력값 초기화.
            numberEdt.setText("")

//사용자 입력값과 비교하여 ?S ?B 검사.
            checkUserInpitStrikeAndBall(inputNum)

        }
    }

    fun checkUserInpitStrikeAndBall(input: String) {

//String을 Int로 변경
        val number = input.toInt()  // "256" => 256

//Int를 ArrayList로 반영
        val numArr = ArrayList<Int>()

        numArr.add(number / 100)    //100의 자리 2: 256 /100 = 2 => Int끼리 나눈경우 Int로 출력
        numArr.add(number / 10 % 10)  //10의 자리 5 :256 / 10 % 10 =5
        numArr.add(number % 10)     //1의 자리 6 : 256 % 10 = 6


        var strikeCount = 0
        var ballCount = 0


        for (i in numArr.indices) {


            for (j in cpuNumList.indices) {


                if (numArr[i] == cpuNumList[j]) {


                    if (i == j) {

                        strikeCount++
                    } else {

                        ballCount++
                    }

                }

            }

        }



        chatList.add(Chat("CPU", "${strikeCount}S ${ballCount}B 입니다."))

        mChatAdapter.notifyDataSetChanged()


        if (strikeCount == 3) {
            chatList.add(Chat("CPU" , "축하합니다!!"))

            Toast.makeText(mContext, "게임을 종료합니다." , Toast.LENGTH_SHORT).show()


            numberEdt.isEnabled = false
            inputBtn.isEnabled = false
        }
    }

    override fun setValues() {


        makeQuestionNum()


        for (num in cpuNumList) {
            Log.d("문제 출제", num.toString())
        }

        chatList.add(Chat("CPU" , "숫자 야구게임에 오신것을 환영합니다."))
        chatList.add(Chat("CPU" , "세자리 숫자를 맞춰주세요."))
        chatList.add(Chat("CPU" , "0은 포함되지 않으며, 중복된 숫자도 없습니다."))

        mChatAdapter = ChatAdapter(mContext, R.layout.chat_list_item, chatList)
        chatListView.adapter = mChatAdapter

    }

    fun makeQuestionNum() {


        for (i in 0..2) {


            while (true) {

                val randomNum = (Math.random()*9+1).toInt()


                var dupCheckResult = true

                for (num in cpuNumList) {
                    if ( num == randomNum) {

                        dupCheckResult = false
                    }
                }


                if(dupCheckResult) {

                    cpuNumList.add(randomNum)

                    break
                }
            }
        }

    }
}
