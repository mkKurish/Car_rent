package com.example.composetest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import com.example.composetest.ui.theme.*

val showFilter = mutableStateOf(false)
val colorFilter = mutableListOf<String>("white", "cherry", "blue", "black", "silver")
val blurState = mutableStateOf(false)

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firstPerson = Person("Jane", "Cooper", 3.4f, R.drawable.profile_pic_1)
        val carList = listOf<Car>(
            Car(
                firstPerson,
                R.drawable.toyota_fortuner_prifile_black,
                "black",
                "Fortuner GR",
                370,
                50,
                45.00f,
                false
            ),
            Car(
                firstPerson,
                R.drawable.chevrolet_tahoe_white,
                "white",
                "Chevy Tahoe",
                526,
                78,
                57.15f,
                false
            ),
            Car(
                firstPerson,
                R.drawable.tesla_modelx_white,
                "white",
                "Model X",
                780,
                85,
                39.50f,
                true
            ),
            Car(
                firstPerson,
                R.drawable.bmw_5_series_blue,
                "blue",
                "5 series",
                173,
                23,
                37.35f,
                false
            ),
            Car(
                firstPerson,
                R.drawable.hyundai_palisade_cherry,
                "cherry",
                "Palisade",
                319,
                47,
                51.05f,
                false
            ),
            Car(
                firstPerson,
                R.drawable.audi_q7_silver,
                "silver",
                "Audi q7",
                243,
                38,
                54.00f,
                false
            ),
            Car(
                firstPerson,
                R.drawable.landrover_rangerover_sport_cherry,
                "cherry",
                "Range Rover Sport",
                310,
                48,
                52.00f,
                false
            ),
            Car(
                firstPerson,
                R.drawable.porsche_taycan_white,
                "white",
                "Taycan",
                416,
                100,
                68.75f,
                true
            ),
            Car(
                firstPerson,
                R.drawable.landrover_rangerover_velar_white,
                "white",
                "Range Rover Velar",
                457,
                70,
                61.65f,
                false
            ),
            Car(
                firstPerson,
                R.drawable.jaguar_ipace_white,
                "white",
                "Jaguar I Pace",
                147,
                24,
                59.95f,
                true
            )
        )
        setContent {
            val interactionSource = remember { MutableInteractionSource() }
            val scrollState = rememberScrollState()
            MainTheme() {
                // Всё поле экрана, которое получит эффект blur при открытии фильтра
                Box(modifier = Modifier.fillMaxSize().blur(if(blurState.value) 3.dp else 0.dp)) {
                    TopPersonFilterBar(this@HomeScreen)
                    ListOfCars(carList, scrollState)
                    BottomNavigationButton(this@HomeScreen, GreetingScreen::class.java)
                }
                /* Очень тормозящее представление. Из-за постоянной отрисовки и скрытия (для эффективности) - эффективность и теряется.
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp, bottom = 77.dp)
                ) {
                    items(carList) { car ->
                        LazyRow() {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .height(if (car.isNearest) 270.dp else 250.dp)
                                        .padding(spacingMedium)
                                ) {
                                    ImageCard(
                                        painter = painterResource(id = car.picId),
                                        contentDescription = if (car.isNearest) "Nearest Car" else "",
                                        title = car.modelName,
                                        curColors = CRATheme.colors,
                                        cost = car.cost,
                                        kms = car.kmsAvailable,
                                        gas = car.gasPercent,
                                        electro = car.isElectromobile
                                    )
                                }
                            }
                            item {
                                Box(modifier = Modifier.height(if (car.isNearest) 270.dp else 250.dp)) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(vertical = spacingMedium)
                                            .padding(end = spacingMedium),
                                        shape = RoundedCornerShape(20.dp),
                                        elevation = 5.dp
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(CRATheme.colors.cardBackground),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(horizontal = spacingMedium),
                                                text = "There would be\nsmth interesting",
                                                style = TextStyle(
                                                    fontSize = 20.sp,
                                                    color = CRATheme.colors.primary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                 */

                if (showFilter.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { /* just to block interactions with other interface*/ },
                        contentAlignment = Alignment.Center
                    ) {
                        Column() {
                            Button(onClick = {
                                if (colorFilter.contains("white")) {
                                    colorFilter.remove("white")
                                } else {
                                    colorFilter.add("white")
                                }
                            }) {
                                Text(text = "Apply/Delete")
                            }
                            Button(onClick = {
                                showFilter.value = false
                                blurState.value = false
                            }) {
                                Text(text = "Close filter")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopPersonFilterBar(
    thisClass: ComponentActivity) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CRATheme.colors.canvasBackground)
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .padding(horizontal = spacingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.clickable {
                    Toast.makeText(
                        thisClass,
                        "Just testing =)", Toast.LENGTH_SHORT
                    ).show()
                }) {
                    ImageValueBlock(
                        painter = painterResource(id = R.drawable.profile_pic_2),
                        picDescr = "personAccount", value = "Mike Wazowski",
                        18, CRATheme.colors.primary, FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(spacingSmall))
                Box(modifier = Modifier.clickable {
                    Toast.makeText(
                        thisClass,
                        "Zero notifications", Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bell),
                        contentDescription = null,
                        tint = CRATheme.colors.primary
                    )
                }
            }
            Box(modifier = Modifier.clickable {
                showFilter.value = true
                blurState.value = true
            }) {
                IconValueBlock(
                    painter = painterResource(id = R.drawable.ic_filter),
                    CRATheme.colors.primary,
                    picDescr = "filterIco", value = "Filter",
                    17, CRATheme.colors.primary, FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ListOfCars(
    carList: List<Car>,
    scrollState: ScrollState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, bottom = 77.dp)
            .verticalScroll(scrollState)
    ) {
        carList.forEach { car ->
            if (car.extColor in colorFilter) {
                LazyRow() {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(if (car.modelName == "Fortuner GR") 270.dp else 250.dp)
                                .padding(spacingMedium)
                        ) {
                            ImageCard(
                                painter = painterResource(id = car.picId),
                                contentDescription = if (car.modelName=="Fortuner GR") "Nearest Car" else "",
                                title = car.modelName,
                                curColors = CRATheme.colors,
                                cost = car.cost,
                                kms = car.kmsAvailable,
                                gas = car.gasPercent,
                                electro = car.isElectomobile
                            )
                        }
                    }
                    item {
                        Box(modifier = Modifier.height(if (car.modelName=="Fortuner GR") 270.dp else 250.dp)) {
                            Card(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = spacingMedium)
                                    .padding(end = spacingMedium),
                                shape = RoundedCornerShape(20.dp),
                                elevation = 5.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(CRATheme.colors.cardBackground),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(horizontal = spacingMedium),
                                        text = "There would be\nsmth interesting",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = CRATheme.colors.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T>BottomNavigationButton(
    from: ComponentActivity,
    refClass: Class<T>
){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button(
            onClick = {
                startActivity(from, Intent(from, refClass), null)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = CRATheme.colors.buttonBackground),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Go Back",
                style = TextStyle(
                    color = CRATheme.colors.buttonText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier,
    curColors: CRAColors,
    cost: Float,
    kms: Int,
    gas: Int,
    electro: Boolean
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = curColors.cardBackground)
                .padding(horizontal = 10.dp)
                .padding(bottom = 46.dp)
                .padding(top = 14.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = contentDescription.uppercase(),
                style = TextStyle(curColors.secondary, fontSize = 14.sp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(modifier = Modifier) {
                    Text(
                        text = title,
                        style = TextStyle(
                            curColors.primary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier) {
                        FullGasArrowBlock(kms, gas, curColors.secondary, electro)
                    }
                    Box(modifier = Modifier) {
                        Text(
                            text = String.format("$%.2f/h", cost),
                            style = TextStyle(
                                color = curColors.primary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageValueBlock(
    painter: Painter,
    picDescr: String,
    value: String,
    sizeOfText: Int,
    colorOfText: Color,
    weightOfFont: FontWeight
) {
    val constraints = ConstraintSet {
        val image = createRefFor("image")
        val value = createRefFor("value")
        val ico = createRefFor("icon")

        constrain(image) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
        }
        constrain(value) {
            start.linkTo(image.end, margin = 4.dp)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
        }
    }
    ConstraintLayout(constraints, modifier = Modifier) {
        Box(
            modifier = Modifier
                .layoutId("image")
                .size(35.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painter,
                contentDescription = picDescr,
                contentScale = ContentScale.Crop
            )
        }
        Box(modifier = Modifier.layoutId("value")) {
            Text(
                text = value,
                style = TextStyle(
                    color = colorOfText,
                    fontSize = sizeOfText.sp,
                    fontWeight = weightOfFont
                )
            )
        }
    }
}

@Composable
fun IconValueBlock(
    painter: Painter,
    iconTint: Color,
    picDescr: String,
    value: String,
    sizeOfText: Int,
    colorOfText: Color,
    weightOfFont: FontWeight
) {
    val constraints = ConstraintSet {
        val sign = createRefFor("sign")
        val value = createRefFor("value")

        constrain(sign) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
        }
        constrain(value) {
            start.linkTo(sign.end, margin = 2.dp)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
        }
    }
    ConstraintLayout(constraints, modifier = Modifier) {
        Box(modifier = Modifier.layoutId("sign")) {
            Icon(
                painter = painter,
                contentDescription = picDescr,
                tint = iconTint
            )
        }
        Box(modifier = Modifier.layoutId("value")) {
            Text(
                text = value,
                style = TextStyle(
                    color = colorOfText,
                    fontSize = sizeOfText.sp,
                    fontWeight = weightOfFont
                )
            )
        }
    }
}

@Composable
fun FullGasArrowBlock(
    val1: Int,
    val2: Int,
    color: Color,
    electro: Boolean
) {
    Row() {
        Box(modifier = Modifier.padding(end = 12.dp))
        {
            IconValueBlock(
                painterResource(id = R.drawable.ic_near_me),
                color,
                "arrowIco",
                "${val1}km",
                16,
                color,
                FontWeight.Normal
            )
        }
        Box(modifier = Modifier)
        {
            IconValueBlock(
                painterResource(id = if (electro) R.drawable.ic_battery else R.drawable.ic_bxs_gas_pump),
                color,
                "gasIco",
                "$val2%",
                16,
                color,
                FontWeight.Normal
            )
        }
    }
}