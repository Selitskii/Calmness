package com.example.calmness

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel

class HelperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.slide1,"Окно регистраций и входа,внизу находится панель навигаций"))
        imageList.add(SlideModel(R.drawable.slide2,"Главная страница.Где вы можете выбрать свое настроение и получить рекоминдаций по нему,а так же послушать подходящию ему музыку.Ну и расчитать индекс массы тела"))
        imageList.add(SlideModel(R.drawable.slide3,"Сдесь вы можеть наити свой гороскоп на сегодня"))
        imageList.add(SlideModel(R.drawable.slide4,"Ваш профиль.Вы можете редактировать его.(Не забываете его изменить)Также сдесь вы можете выйти из акаунта(Нажмите выход и закроите приложение)"))
        imageList.add(SlideModel(R.drawable.slide5,"Настроики"))
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

    }


}