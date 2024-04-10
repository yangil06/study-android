package com.example.composedemo

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.ui.theme.ComposeDemoTheme

import androidx.compose.ui.Alignment
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //트리구조가
        //ComposeDemoTheme -> Surface ->
        //DemoScreen -> Column ->
                                // DemoText, Spacer, DemoSlider, Text
                                     //-> Text         //->Slider
        //이중에 DemoText, DemoSlider 를 구현할 것.
        setContent {
            ComposeDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //5) DemoSceen()을 넣어서 완성한다.
                    DemoScreen()
                }
            }
        }

        //Live Templates
        //Ctrl + .,
        //toast and tab -> Toast 자주 쓰는 템플릿 기능
        //쓸 수 있는 영역에서만 써야함
        //함수 밖에서 하려니 안되넹..
        Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT).show()
        //이 때는 Ctrl + Space 눌러서
        //applicatoinContext 도 잘 검색됨..
    }

    //Code Generation
    // Alt + Insert
    //notify for destroying
    //add onStop <- stubMethod <- Override Methods..
    override fun onStop() {
        super.onStop()
    }
    //Ctrl+Shift+(+/-)
    //코드를 시그니처만 볼 수 있는 단축키

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }
}

//1) 첫째로 DemoText Composable Component 추가
@Composable
//fun DemoText() {
fun DemoText(message: String, fontSize: Float) {
    Text(
        text = message,
        fontSize = fontSize.sp, //sp없을 때 옆의 에러코드 처리가 알아서 알려주네..
        fontWeight = FontWeight.Bold
    )
}
//2) Preview를 추가하려는데, non-default parameter 는 지원하지 않는단다.
@Preview(showBackground = true)
@Composable
fun DemoTextPreview() {
    DemoText(message = "Welcome to Compose", fontSize = 12f)
}

//3) DemoSlider Composable 추가
@Composable
fun DemoSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    Slider(
        modifier = Modifier.padding(10.dp),
        valueRange = 20f..40f,
        value = sliderPosition,
        //onValueChange = {onPositionChange(it)}//onPositionChange 에는 value 가 들어가야 되는데..
    //it 이 그 내용을 포괄하는건가..
    //잘 모르겠네..
    //기억에 코틀린에서 it 람다는.. 인자 대신 썼던거 같은데..
    //그럼 주석처리하고 한번 써보자..
//        onValueChange = {sliderPosition ->
//            onPositionChange(sliderPosition)
//        }
        //일케 했던거 같고.. 이걸 it으로 포괄하려고 하면
        //onValueChange = {onPositionChange(it)}
        //해서 인자를 자동추론 하는 거 같네..
    //이벤트 핸들러의 파라미터 이름(onPositionChange)만 할당한 뒤
    //컴파일러가 현재 값 전달을 처리하는 방식으로 이를 더욱 단순화 할 수 있다.
        onValueChange = onPositionChange
    )
}

//4) DemoScreen Composable 추가하기
@Composable
fun DemoScreen() {
    var sliderPosition by remember {mutableStateOf(20f)}
    val handlePositionChange = {position : Float ->
        sliderPosition = position
    }
    //컴포즈 런타임 시스템은 사용자가 슬라이더 값을 변경했을 경우
    //handlePositionChange 의 값 변화를 인지하고
    //사용자 인터페이스를 재구성한다.
    //이 과정에서 DemoScreen() Composable을 호출하며
    //이 때 변수를 20으로 재초기화한다.
    //remember를 사용해 재구성하는 동안 현재값을 기억해야 한다고
    //컴포즈에 알려야 한다.

    //마지막으로 필수 컴포저블 함수를 포함한 Column을 DemoScreen에 추가
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        DemoText(message = "Welcome to Compose", fontSize = sliderPosition)

        Spacer(modifier = Modifier.height(150.dp))

        DemoSlider(
            sliderPosition = sliderPosition,
            onPositionChange = handlePositionChange
        )

        Text(
            style = MaterialTheme.typography.h2,
            text = sliderPosition.toInt().toString() + "sp"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    DemoScreen()
}

//Statement completion
// Ctrl + Shift + Enter
// fun myMethod() 까지만 추가하고 위의 키를 누르면 줄괄호가 자동으로 추가된다.
fun myMethod() {
    //Available Parameters info
    //Ctrl + p <- shortcut 이 Windows 일 때만 적용됨..
    val myButtonText: String = String.format(
        locale = Locale.KOREAN,
        format = "Test"
    )

    //Alt + F8 -> Finding Sample code
    //val FindingSampleCode : String = String.format
    //format 이 안되서 찾으려고 했더니 안찾아지네..
    //Activity 쪽에 가서 찾아보자..

    //안찾아진다..--;;
    //전문가에게 물어봐야겠당..
}


