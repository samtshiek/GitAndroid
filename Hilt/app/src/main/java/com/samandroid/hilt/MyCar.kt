package com.samandroid.hilt

import dagger.Provides
import javax.inject.Inject

class MyCar @Inject constructor() {
    var make: String = "Volvo"
}