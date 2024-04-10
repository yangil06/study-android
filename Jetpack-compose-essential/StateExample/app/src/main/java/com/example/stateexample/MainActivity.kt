package com.example.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stateexample.ui.theme.StateExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DemoScreen()
                }
            }
        }
    }
}

@Composable
fun DemoScreen() {
    MyTextField()
}

@Composable
fun MyTextField() {
    //하나의 상태변수, 사용자 입력에 따라 상태를 변경하는
    // 하나의 이벤트 핸들러를 포함한 상태 컴포저블 함수로 만들어보자.
    var textStateTemp = { mutableStateOf("") }
    // state 가 람다로 저장됬네?
    //빈 인자를 받아 mutableState를 반환하는 람다
    //() -> mutableState("")
    var valueRememberedTextState = remember { mutableStateOf("") }
    var (textValue, setText) = remember { mutableStateOf("") } //valueRememberedTextState 의 멤버를 분할해서 담는다.

    var textState by remember { mutableStateOf("") }


    val onTextChange = { text : String -> //안드로이드 상태 변화 리스너에 상태값 수정을 넣는다.
        //valueRememberedTextState.value = text
        textState = text //setter(text) 동작
        setText(text)
    }
    TextField(
        value = textState,//getter() 동작
        onValueChange = onTextChange //onValueChange 에 마우스 오른쪽을 갖다 대면 (String) -> Unit임을 알 수 있다.
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StateExampleTheme {
        DemoScreen()
    }
}