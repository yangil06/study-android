package com.example.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                // 테마의 'background' 색상을 이용하는 서피스 컨테이너
                // 여기서 Surface 컴포넌트는 전체 화면을 채우고 배경 색상을 안드로이드의 머티리얼 디자인 테마에
                // 정의된 표준 색상으로 설정한다. 머티리얼 디자인은 모든 안드로이드 앱에서
                // 일관된 형태와 느낌을 제공하기 위해 구글에서 개발한 디자인 지침이다.
                // Surface 컴포넌트
                // Component 조합 -> Composable

                // 미리보기는 Composable 함수들에 의해 만들어진 형태
                // 즉 시뮬레이터를 돌릴 필요가 없는것.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//Preview 에 showSystemUi = true를 하면, statusbar같은 시스템 UI가 보인다.
@Preview(showBackground = true, showSystemUi = true)
//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoTheme {
        Greeting("Compose")
    }
}