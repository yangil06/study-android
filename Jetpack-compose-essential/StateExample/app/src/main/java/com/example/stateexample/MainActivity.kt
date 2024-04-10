package com.example.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
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
                    //DemoScreen()
                    //FunctionA()
                    //Column 없이 했더니..
                    //맨 마지막게 나오나 보네.. Switch 위젯만 보임
                    showComponents()
                }
            }
        }
    }
}

@Composable
fun DemoScreen() {
    var textState by remember { mutableStateOf("") }
    val onTextChange = { text : String ->
        textState = text //setter(text) 동작
    }
    MyTextField(textState, onTextChange)
}

//State hoisting
//상태를 부모쪽으로 이동함을 의미
//사용자가 입력한 텍스트가 함수 호출에 접근할 수 없기 때문에
//그 형제 함수들에도 전달할 수 없고
//다른 상태나 이벤트 핸들러도 함수로 전달할 수 없으므로 재사용성이 저하된다.
//부모로 들어올리면 이 문제들이 일부 해결되나보다..--;;
//어떻게 보면, 공통변수를 부모클래스로 끌어올려서 자식클래스들에서
//공통적으로 이용할 수 있게 한 것과 별 다른게 없는 행위이다.
//UI 값이라는 측면에서 좀 더 의미가 다를 순 있겠지만...
@Composable
fun MyTextField(textState: String, onTextChange : (String) -> Unit) {
    TextField(
        value = textState,//getter() 동작
        //onTextChange = onTextChange, ctrl 을 눌러서 들어가보면, onTextChange라는 파라미터는 없음
        onValueChange = onTextChange
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StateExampleTheme {
        showComponents()
    }
}

@Composable
fun showComponents() {
    Column {
        DemoScreen()
    }
}