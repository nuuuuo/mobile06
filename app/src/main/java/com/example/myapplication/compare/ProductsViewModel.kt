package com.example.myapplication.compare
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

class ProductViewModel : ViewModel() {
    // 제품 리스트를 mutableStateListOf를 사용하여 선언
    val products = mutableStateListOf<Product>()

    init {
        loadInitialProducts()
    }

    private fun loadInitialProducts() {
        products.addAll(listOf(
            Product(
                name = "강냉이/팝콘_33한체다치즈팝콘",
                energy = 478.0,
                carbohydrates = 66.2,
                sugars = 11.9,
                fat = 19.5,
                protein = 9.34,
                sodium = 594.0,
                saturatedFat = 11.6,
                cholesterol = 0.0
            ),
            Product(
                name = "강냉이/팝콘_CGV시그니처스위트팝콘",
                energy = 529.0,
                carbohydrates = 64.29,
                sugars = 7.14,
                fat = 28.57,
                protein = 4.29,
                sodium = 221.0,
                saturatedFat = 7.14,
                cholesterol = 0.0
            ),
            Product(
                name = "강냉이/팝콘_CGV시그니처카라멜&치즈팝콘",
                energy = 479.0,
                carbohydrates = 68.42,
                sugars = 28.42,
                fat = 20.0,
                protein = 6.32,
                sodium = 516.0,
                saturatedFat = 8.42,
                cholesterol = 0.0
            ),
            Product(
                name = "강냉이/팝콘_dardaby커널스슈퍼믹스팝콘",
                energy = 473.0,
                carbohydrates = 72.73,
                sugars = 16.36,
                fat = 20.0,
                protein = 4.0,
                sodium = 182.0,
                saturatedFat = 9.09,
                cholesterol = 0.0
            ),
            Product(
                name = "강냉이/팝콘_dardaby커널스씨네마팝콘",
                energy = 559.0,
                carbohydrates = 60.0,
                sugars = 0.0,
                fat = 32.73,
                protein = 6.36,
                sodium = 245.0,
                saturatedFat = 7.27,
                cholesterol = 0.0
            ),
            Product(
                name = "강냉이/팝콘_dardaby커널스씨네마팝콘카라멜맛",
                energy = 500.0,
                carbohydrates = 71.07,
                sugars = 0.0,
                fat = 21.79,
                protein = 5.0,
                sodium = 188.0,
                saturatedFat = 6.07,
                cholesterol = 0.0
            ),
            Product(
                name = "강냉이/팝콘_dardaby커널스치즈케이크맛팝콘",
                energy = 540.0,
                carbohydrates = 60.0,
                sugars = 40.0,  // Assumed value, please verify
                fat = 32.0,
                protein = 4.0,
                sodium = 440.0,
                saturatedFat = 12.0,
                cholesterol = 0.0
            ),
            Product(
                name = "강냉이/팝콘_dardaby커널스카라멜&크림치즈팝콘",
                energy = 467.0,
                carbohydrates = 60.0,
                sugars = 28.89,
                fat = 24.44,
                protein = 2.67,
                sodium = 493.0,
                saturatedFat = 12.0,
                cholesterol = 0.8
            )
        ))
    }

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun removeProduct(product: Product) {
        products.remove(product)
    }
}