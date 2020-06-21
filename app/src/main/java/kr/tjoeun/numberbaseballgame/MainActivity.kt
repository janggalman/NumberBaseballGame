package kr.tjoeun.numberbaseballgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.tjoeun.numberbaseballgame.adapters.ChatAdapter
import kr.tjoeun.numberbaseballgame.datas.Chat

class MainActivity : BaseActivity() {

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
//            사용자 입력값을 받어오자
            val inputNum = numberEdt.text.toString()

//            세자리 일때만 OK
            if (inputNum.length != 3)
            {
                Toast.makeText(mContext, "세자리 숫자만 입력 가능합니다." , Toast.LENGTH_SHORT).show()
//                이벤트 강제 종료
                return@setOnClickListener
            }

//            입력값이 3자리임이 보증됨.
//            사용자가 한말로 입력처리 => 채팅 내역으로 추가

            chatList.add(Chat("ME",inputNum))

            mChatAdapter.notifyDataSetChanged()
            
//            한번 숫자 입력시 입력칸 비워주기
            numberEdt.setText("")

        }


    }

    override fun setValues() {

        chatList.add(Chat("CPU" , "숫자 야구게임에 오신것을 환영합니다."))
        chatList.add(Chat("CPU" , "세자리 숫자를 맞춰주세요."))
        chatList.add(Chat("CPU" , "0은 포함되지 않으며, 중복된 숫자도 없습니다."))

        mChatAdapter = ChatAdapter(mContext, R.layout.chat_list_item, chatList)
        chatListView.adapter = mChatAdapter

    }
}
