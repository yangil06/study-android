package com.example.composedemo

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
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
import androidx.compose.ui.text.toUpperCase
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
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

        nullExceptionTest()
        //and run Ctrl+F9, Shift+F10
        //Shift+F9 Debug, Step F8, into F7
        //Continue F9

        //안전한 타입변환 연산자는
        //캐스팅을 할 수 없으면 null을 반환한다.
        val keyMgr = getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager

        //비트 연산자나 논리연산자에 대해서는 축약형 함수를 제공
        /*
        val int1 : Int
        int1.inv()
        int1.and()
        int1.or
        int1.xor()
        int1.shl
        int1.shr
         */

        //compose declarative, data-driven 버그에 취약하지 않도록
        //컴포즈를 이용하면, 레이아웃을 빌드하는 접근 방식의 복잡성에 신경쓰지 않고
        //사용자 인터페이스가 표시되는 방식을 선언함으로써 레이아웃을 만들 수 있다.
        //프로그래머가 선언만 하면 레이아웃 배치, 제한, 렌더링 방법에 관한 모든
        //복잡한 세부사항을 컴포즈가 자동으로 처리한다.

        //컴포즈 도입 이전에 안드로이드 앱에는 앱 내 데이터의 현재 값을 확인하는 코드를
        //작성해야 했다.
        //예를 들면??
        //데이터 변경사항을 자주 확인하는 코드, 사용자가 데이터 업데이트를 요청할 수 있는
        //새로 고침 옵션을 제공하는 등..
        //사용자 인터페이스 상태를 인관되게 유지하건, 토글 버튼 설정등이 적절하게 저장되었는지 확인해야 할때
        //앱의 여러 영역에서 동일한 데이터 소스에 의존할 수록 이런 요구사항은 더욱 복잡해진다

        //컴포즈는 State 기반 시스템을 제공해 이런 복잡성을 해결한다.
        //데이터의 변경을 확인하는 코드를 넣지 않아도, 변경사항이 사용자 인터페이스에 자동으로
        //반영된다. 상태에 접근하는 모든 사용자 인터페이스 컴포넌트는
        //기본적으로 그 상태를 구독한다.
/*
        var sliderPosition by remember {mutableStateOf(20f)}
        val handlePositionChange = {position : Float ->
            sliderPosition = position
        }
 */
        //이것을 예로 들면, HomeScreen 이 sliderPosition 을 구독하게
        //된다는 것 같다.
        //따라서 sliderPosition 이 변경되면,
        //HomeScreen 의 Recomposition 이 일어난다는 것.
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

//Chapter10 안드로이드 런타임 설명
/*
안드로이드 스튜디오 안에서 안드로이드 앱을 빌드하면 중간 바이트코드 형식(DEX)으로
컴파일된다.
이후 기기에 애플리케이션이 기기에 설치되면 안드로이드 런타임은(ART) Ahead=of-Time
컴파일이라 불리는 프로세스를 통해 바이트코드를 기기의 프로세스가 요청하는
기본 명령으로 변환한다. 이 형식을 실행 및 연결 가능한 형식
(Executable and Linkable Format, ELF)이라 부른다.
이후 애플리케이션이 실행될 때마다 ELF 실행 버전이 실행되어
애플리케이션 성능이 빨라지고 배터리 수명이 향상된다.
이는 애플리케이션을 시작할 때마다 가상 머신안에서 바이트코드를 변환했던
이전 안드로이드 구현에서의 JIT 컴파일 접근 방식과 대조된다.
*/

fun nullExceptionTest() : String {
    val username: String? = null
    username?.length //null이면 수행 안되는게 safecall인가 보넹..
    print(username?.length)

    //let 함수를 호출하면 안전 호출 연산자를 사용했으므로
    //변수에 null이 아닌 값이 할당되었을 때만 let 함수를 호출한다.
    username?.let {
        print(username.length)
    }

    //lateinit 초기화 지연
    lateinit var myName: String
//    print("My name is " + myName)
    //throwing exception
    //if (myName.isInitialized) { } <- 안되는디..

    //elvis
    return myName ?: "String is null"
}