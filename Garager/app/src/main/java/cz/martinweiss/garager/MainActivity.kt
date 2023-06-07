package cz.martinweiss.garager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.datastore.*
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.navigation.Destination
import cz.martinweiss.garager.navigation.NavGraph
import cz.martinweiss.garager.ui.theme.GaragerTheme
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


class MainActivity : ComponentActivity(), KoinComponent {
    val vehiclesRepository = get<IVehiclesRepository>()
    val dataStoreController = get<IDataStoreController>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Splash Screen | LOADING
        val splashScreen = installSplashScreen()
        var keepSplashScreenVisible = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreenVisible }

        val manufacturers = listOf(
            "Acura", "Alfa Romeo", "AM General", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick", "Cadillac", "Chevrolet", "Chrysler", "Daewoo", "Datsun", "Dodge", "Eagle", "Ferrari", "FIAT", "Fisker", "Ford", "Genesis", "Geo", "GMC", "Honda", "HUMMER", "Hyundai", "Infiniti", "Isuzu", "Jaguar", "Jeep", "Kia", "Lamborghini", "Land Rover", "Lexus", "Lincoln", "Lotus", "Maserati", "Maybach", "Mazda", "McLaren", "Mercedes-Benz", "Mercury", "MINI", "Mitsubishi", "Nissan", "Oldsmobile", "Panoz", "Plymouth", "Pontiac", "Porsche", "Ram", "Rolls-Royce", "Saab", "Saturn", "Scion", "Smart", "Sterling", "Subaru", "Suzuki", "Tesla", "Toyota", "Volkswagen", "Volvo"
        )

        lifecycleScope.launch {
            // [MANUFACTURERS]
            val currentManufacturers = vehiclesRepository.getManufacturers().map { it.name }
            val missingManufacturers = manufacturers - currentManufacturers.toSet()
            missingManufacturers.forEach {
                vehiclesRepository.insertManufacturer(Manufacturer(name = it))
            }

            // [DATASTORE]
            val motWarning = dataStoreController.getIntByKey(DATASTORE_MOT_DAYS)
            val currency = dataStoreController.getStringByKey(DATASTORE_CURRENCY)

            if(motWarning == null) {
                dataStoreController.updateIntKey(DATASTORE_MOT_DAYS, DEFAULT_MOT_WARNING_DAYS_VALUE)
            }

            if(currency == null) {
                dataStoreController.updateStringKey(DATASTORE_CURRENCY, DEFAULT_CURRENCY_VALUE)
            }

            // [FINISH]
            //delay(3000)
            keepSplashScreenVisible = false
        }
        //Splash Screen | LOADING

        setContent {
            GaragerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(startDestination = Destination.VehicleListScreen.route)
                }
            }
        }
    }
}