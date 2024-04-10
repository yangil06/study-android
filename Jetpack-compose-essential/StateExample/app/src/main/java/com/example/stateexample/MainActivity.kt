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
    MyTextField()
}

@Composable
fun MyTextField() {
    var textState by remember { mutableStateOf("") }
    val onTextChange = { text : String ->
        textState = text //setter(text) 동작
    }
    TextField(
        value = textState,//getter() 동작
        //onTextChange = onTextChange, ctrl 을 눌러서 들어가보면, onTextChange라는 파라미터는 없음
        onValueChange = onTextChange
    )
}

//unidirectional data flow 개념
@Composable
fun FunctionA() {
    var switchState by remember { mutableStateOf(true) }
    val onSwitchChange = { value : Boolean ->
        switchState = value
    }
    FunctionB(
        switchState = switchState,
        onSwitchChange = onSwitchChange
    )
}

@Composable
fun FunctionB(switchState: Boolean, onSwitchChange : (Boolean) -> Unit ) {
    Switch(
        checked = switchState,
        onCheckedChange = onSwitchChange
    )
}
//이 프로세스에서의 핵심은 switchState에 할당된 값은 FunctionA에서만 직접 변경을 다루고
//FunctionB에 의해 직접 업데이트되지 않는다는 점이다.
//Switch설정은 FunctionB가 직접 on 위치에서 off 위치로 바꾸지 않는다.
//대신 FunctionA의 이벤트 핸들러를 호출해 상태를 변경하고,
//그 결과를 이용해 재구성함으로써 Switch를 새로운 윗치값으로 만든다.
//규칙에 의해
//이벤트는 Switch->FunctionB->FunctionA 에서 변경이 일어난다.
//변경이 일어나서, switchState을 subscribe 한 FunctionA는
// switchState = value 에 의해,
// Compose Runtime 에 Observerable 함수를 트리거하게 되고
// 그 트리거는, FunctionA를 트리거하여 recomposition 한다
// 이 때 switchState 의 데이터, switchState.value, 즉 위젯에 필요한 데이터는
// FunctionA -> FunctionB(switchState) -> Switch(switchState)
// 로 bool 값이 상태의 getter에 의해
//Switch 로 전달된다.

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
        FunctionA()
    }
}