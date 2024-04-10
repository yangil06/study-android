package com.example.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
    //remember 대신 rememberSavable을 사용하면
    //configurationChange 에 의해 activity가 재생성 되어도
    //값들을 Bundle에 유지할 수 있다.
    //var textState by remember { mutableStateOf("") }
    var textState by rememberSaveable{ mutableStateOf("") }
    val onTextChange = { text : String ->
        textState = text //setter(text) 동작
    }
    MyTextField(textState, onTextChange)
}

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