package com.example.data.knowledge

import com.example.data.model.ConnectionProtocol
import com.example.data.model.DeviceCatalogItem
import com.example.data.model.DeviceCategory
import com.example.data.model.DeviceEntity
import java.util.UUID

object DeviceKnowledgeBase {

    val catalog: List<DeviceCatalogItem> = listOf(
        // Wearables / Fitbit / Balco / Fitness
        DeviceCatalogItem(
            id = "balco-fitness-tracker",
            name = "Balco Bluetooth Fitness Tracker",
            modelName = "FT-880 OLED Heart Rate & Pedometer",
            manufacturer = "Balco Lifestyle",
            manufacturerCountry = "Australia / Germany",
            category = DeviceCategory.WEARABLE,
            devicePurpose = "Versatile Bluetooth activity & biometric band with optical heart rate monitor, pedometer, calorie burn tracking, and sleep stages.",
            keyCapabilities = listOf(
                "Dynamic Optical PPG Heart Rate (BPM) & Resting Pulse",
                "Daily Calorie Burn Estimator & Active Zones",
                "Real-time Step Pedometer & Distance in km/miles",
                "Sleep Stages analysis (Deep, Light, REM)",
                "OLED Touch Display & IP67 Splash Resistance"
            ),
            howItWorks = "Directly queries BLE GATT 0x180D (Heart Rate Service) and 0x1814 (Running Speed & Cadence) over Bluetooth. allConnect extracts your current heart rate, calories burned, and steps in real-time without requiring any third-party app.",
            defaultProtocol = ConnectionProtocol.BLE,
            defaultBatteryPowered = true,
            typicalBatteryPct = 94,
            defaultRoom = "Wearables / On Person",
            samplePrimaryLabel = "Heart Rate & Calories",
            samplePrimaryValue = "74 bpm • 480 kcal",
            sampleSecondaryLabel = "Steps Today",
            sampleSecondaryValue = "7,350 / 10,000 steps",
            pairingInstructions = "Ensure the Balco band is charged. Hold the touch key on the tracker for 3 seconds to turn on Bluetooth advertising."
        ),
        DeviceCatalogItem(
            id = "fitbit-charge-6",
            name = "Fitbit Charge 6",
            modelName = "FB423 (Charge 6)",
            manufacturer = "Fitbit (Google)",
            manufacturerCountry = "USA",
            category = DeviceCategory.WEARABLE,
            devicePurpose = "Advanced health, fitness, and heart-rate tracker with built-in GPS, ECG app, daily readiness score, and sleep analysis.",
            keyCapabilities = listOf(
                "Continuous 24/7 Heart Rate & EDA Stress Scan",
                "Built-in GPS for pace & real-time distance",
                "Sleep Stages breakdown & SpO2 blood oxygen",
                "Google Maps turn-by-turn & Google Wallet contactless pay",
                "Water resistant up to 50 meters (Swimproof)"
            ),
            howItWorks = "Directly connects via Bluetooth Low Energy (BLE) GATT profile. allConnect streams live step counts, active heart rate, and syncs sleep metrics directly to your device without requiring the proprietary Fitbit cloud app.",
            defaultProtocol = ConnectionProtocol.BLE,
            defaultBatteryPowered = true,
            typicalBatteryPct = 92,
            defaultRoom = "Wearables / On Person",
            samplePrimaryLabel = "Steps Today",
            samplePrimaryValue = "9,420 steps",
            sampleSecondaryLabel = "Heart Rate",
            sampleSecondaryValue = "71 bpm (Resting: 62)",
            pairingInstructions = "Turn on Bluetooth on your phone. Put your Fitbit on its magnetic charger to wake pairing mode. Enter the 4-digit code shown on the screen."
        ),
        DeviceCatalogItem(
            id = "fitbit-sense-2",
            name = "Fitbit Sense 2",
            modelName = "FB521 (Sense 2)",
            manufacturer = "Fitbit (Google)",
            manufacturerCountry = "USA",
            category = DeviceCategory.WEARABLE,
            devicePurpose = "Premium health & smartwatch focused on stress management, cEDA body response tracking, ECG assessment, and sleep tracking.",
            keyCapabilities = listOf(
                "All-day continuous cEDA body response stress tracking",
                "ECG App for atrial fibrillation detection",
                "Skin temperature sensor & nightly variance",
                "High & Low Heart Rate Notifications",
                "6+ day battery life with fast charging"
            ),
            howItWorks = "Communicates over BLE standard health profiles. allConnect extracts real-time biometric metrics, battery telemetry, and exercise status locally.",
            defaultProtocol = ConnectionProtocol.BLE,
            defaultBatteryPowered = true,
            typicalBatteryPct = 78,
            defaultRoom = "Wearables / On Person",
            samplePrimaryLabel = "Daily Readiness",
            samplePrimaryValue = "84 / 100 (Optimal)",
            sampleSecondaryLabel = "Stress Response",
            sampleSecondaryValue = "Low (Calm state)",
            pairingInstructions = "Press and hold the side button for 3 seconds until the Fitbit logo pulses, then tap Pair in allConnect."
        ),
        DeviceCatalogItem(
            id = "garmin-forerunner-265",
            name = "Garmin Forerunner 265",
            modelName = "FR265 GPS",
            manufacturer = "Garmin Ltd.",
            manufacturerCountry = "Switzerland / USA",
            category = DeviceCategory.WEARABLE,
            devicePurpose = "Dedicated running and multisport smartwatch with AMOLED touchscreen, training readiness metrics, and multi-band GNSS tracking.",
            keyCapabilities = listOf(
                "Morning Report with HRV status and training forecast",
                "Multi-Band GPS for pinpoint urban/trail tracking",
                "Running Dynamics & Power directly from wrist",
                "Garmin Pay & offline music sync"
            ),
            howItWorks = "Connects via BLE ANT+ dual-mode. allConnect pulls workout summaries and battery status directly.",
            defaultProtocol = ConnectionProtocol.BLE,
            defaultBatteryPowered = true,
            typicalBatteryPct = 88,
            defaultRoom = "Wearables / On Person",
            samplePrimaryLabel = "Training Readiness",
            samplePrimaryValue = "79 (Ready to train)",
            sampleSecondaryLabel = "HRV Status",
            sampleSecondaryValue = "54 ms (Balanced)",
            pairingInstructions = "On watch: Settings > Connectivity > Phone > Pair Phone. allConnect will auto-negotiate passkey."
        ),
        DeviceCatalogItem(
            id = "apple-watch-ultra",
            name = "Apple Watch Ultra 2",
            modelName = "A2986 / S9 SiP",
            manufacturer = "Apple Inc.",
            manufacturerCountry = "USA",
            category = DeviceCategory.WEARABLE,
            devicePurpose = "Rugged smartwatch with precision dual-frequency GPS, depth gauge for diving, 3000-nit display, and emergency siren.",
            keyCapabilities = listOf(
                "Aerospace-grade titanium case with sapphire crystal",
                "Depth gauge and water temperature sensor up to 40m",
                "ECG, blood oxygen, and crash detection",
                "Custom Action Button & Dual speakers"
            ),
            howItWorks = "Discovered over BLE proximity advertising. allConnect connects directly to standard Bluetooth health & sensor services.",
            defaultProtocol = ConnectionProtocol.BLE,
            defaultBatteryPowered = true,
            typicalBatteryPct = 85,
            defaultRoom = "Wearables / On Person",
            samplePrimaryLabel = "Active Calories",
            samplePrimaryValue = "640 / 800 kcal",
            sampleSecondaryLabel = "Vitals Status",
            sampleSecondaryValue = "Normal Ranges",
            pairingInstructions = "Enable Bluetooth on watch in Settings > Bluetooth and make discoverable."
        ),

        // Smart Home Hubs & Bridges
        DeviceCatalogItem(
            id = "philips-hue-bridge",
            name = "Philips Hue Bridge 2.1",
            modelName = "BSB002 (Zigbee 3.0)",
            manufacturer = "Signify (Philips Hue)",
            manufacturerCountry = "Netherlands",
            category = DeviceCategory.SMART_HUB,
            devicePurpose = "Central smart lighting hub that creates a secure local Zigbee 3.0 mesh network for up to 50 lights and 12 accessories.",
            keyCapabilities = listOf(
                "Local Zigbee 3.0 Mesh with sub-second latency",
                "Matter controller enabled for cross-platform linking",
                "Dynamic lighting scenes, schedules, and sunrise alarms",
                "Works completely offline on your local network"
            ),
            howItWorks = "allConnect discovers the Hue Bridge instantly via local network mDNS / SSDP HTTP API. One tap on the bridge link button establishes encrypted local control—no cloud account or extra Philips app required.",
            defaultProtocol = ConnectionProtocol.ZIGBEE_BRIDGE,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Living Room",
            samplePrimaryLabel = "Connected Lights",
            samplePrimaryValue = "14 Bulbs & Strips",
            sampleSecondaryLabel = "Mesh Network",
            sampleSecondaryValue = "Zigbee Ch 25 (Optimal)",
            pairingInstructions = "Press the large circular Link button on top of the physical Hue Bridge when prompted by allConnect."
        ),
        DeviceCatalogItem(
            id = "samsung-smartthings-hub",
            name = "SmartThings Station / Hub v3",
            modelName = "IM6001-V3P22",
            manufacturer = "Samsung SmartThings",
            manufacturerCountry = "South Korea",
            category = DeviceCategory.SMART_HUB,
            devicePurpose = "Universal multi-protocol smart home hub supporting Zigbee, Z-Wave, Thread, and Matter devices for whole-home automation.",
            keyCapabilities = listOf(
                "Zigbee 3.0 & Z-Wave Plus long-range radios",
                "Matter over Thread Border Router built-in",
                "Local automation engine running routines offline",
                "15W Wireless charging pad on top (Station model)"
            ),
            howItWorks = "allConnect connects via local SSDP and Matter commissioner protocol to synchronize all child sensors and switches across your home.",
            defaultProtocol = ConnectionProtocol.MATTER_THREAD,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Hallway",
            samplePrimaryLabel = "Paired Devices",
            samplePrimaryValue = "28 Nodes Connected",
            sampleSecondaryLabel = "Thread Mesh",
            sampleSecondaryValue = "Leader Router (Active)",
            pairingInstructions = "Locate the 8-digit Matter setup code or QR on the bottom label of the Hub."
        ),
        DeviceCatalogItem(
            id = "home-assistant-yellow",
            name = "Home Assistant Yellow Hub",
            modelName = "HAY-001 (CM4)",
            manufacturer = "Nabu Casa / Home Assistant",
            manufacturerCountry = "Open Source / Global",
            category = DeviceCategory.SMART_HUB,
            devicePurpose = "Privacy-first local home automation coordinator featuring native Silicon Labs Zigbee/Thread radio and Gigabit Ethernet.",
            keyCapabilities = listOf(
                "100% Local data processing & private telemetry",
                "Multiprotocol Zigbee / Thread coordinator chip",
                "Supports over 2,000 smart home integrations natively",
                "M.2 NVMe SSD storage slot for lifetime event logs"
            ),
            howItWorks = "allConnect queries the local WebSocket API with encrypted token to grant unified control over all entities.",
            defaultProtocol = ConnectionProtocol.WIFI_LOCAL,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Home Office",
            samplePrimaryLabel = "Total Entities",
            samplePrimaryValue = "142 Smart Entities",
            sampleSecondaryLabel = "Local CPU / RAM",
            sampleSecondaryValue = "12% Load • 38°C",
            pairingInstructions = "Enable local API in Home Assistant settings and approve allConnect integration."
        ),
        DeviceCatalogItem(
            id = "aqara-hub-m3",
            name = "Aqara Hub M3",
            modelName = "HM-M03 (Multi-Protocol)",
            manufacturer = "Aqara (Lumi United)",
            manufacturerCountry = "China",
            category = DeviceCategory.SMART_HUB,
            devicePurpose = "Next-gen smart home hub with 360° infrared blaster, Thread border router, Zigbee controller, and PoE power.",
            keyCapabilities = listOf(
                "360° IR blaster to control legacy AC and TVs",
                "Matter controller and Thread border router",
                "PoE (Power over Ethernet) or USB-C powered",
                "Dual-band Wi-Fi 2.4/5GHz + Bluetooth 5.1"
            ),
            howItWorks = "Discovered via Matter broadcast. allConnect bridges IR controls and Aqara temperature/motion sensors directly into your unified dashboard.",
            defaultProtocol = ConnectionProtocol.MATTER_THREAD,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Living Room",
            samplePrimaryLabel = "IR & Sensors",
            samplePrimaryValue = "9 Aqara Sensors Active",
            sampleSecondaryLabel = "IR Blaster",
            sampleSecondaryValue = "AC & TV Linked",
            pairingInstructions = "Hold the reset button for 10 seconds until the yellow LED pulses, then scan Matter code."
        ),

        // Climate & Thermostats
        DeviceCatalogItem(
            id = "google-nest-thermostat",
            name = "Google Nest Learning Thermostat",
            modelName = "Nest 4th Gen (T4000)",
            manufacturer = "Google Nest",
            manufacturerCountry = "USA",
            category = DeviceCategory.CLIMATE,
            devicePurpose = "Smart programmable thermostat that optimizes heating/cooling energy, detects home/away presence, and balances humidity.",
            keyCapabilities = listOf(
                "Matter-certified local smart climate control",
                "Farsight high-res borderless glass display",
                "HVAC health monitoring and filter change alerts",
                "Auto-Schedule learns your family preferences"
            ),
            howItWorks = "Communicates over local Wi-Fi / Matter. allConnect allows adjusting temperature setpoints, toggling Eco mode, and reading ambient humidity in real-time.",
            defaultProtocol = ConnectionProtocol.WIFI_LOCAL,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Hallway",
            samplePrimaryLabel = "Target Temp",
            samplePrimaryValue = "70.5°F (Heating)",
            sampleSecondaryLabel = "Ambient Climate",
            sampleSecondaryValue = "69.0°F • 45% Humidity",
            pairingInstructions = "Go to Settings > Matter on the Nest Thermostat wheel and display the 11-digit code."
        ),
        DeviceCatalogItem(
            id = "ecobee-smart-thermostat",
            name = "Ecobee Smart Thermostat Premium",
            modelName = "EB-STATE6-01",
            manufacturer = "Ecobee Inc.",
            manufacturerCountry = "Canada",
            category = DeviceCategory.CLIMATE,
            devicePurpose = "Whole-home climate controller with built-in indoor air quality (VOC & CO2) monitor, occupancy radar, and SmartSensor support.",
            keyCapabilities = listOf(
                "Built-in Indoor Air Quality (VOC, CO2, Humidity) monitor",
                "Includes SmartSensor for room occupancy balancing",
                "Zinc die-cast front chassis with glass display",
                "Built-in speaker with chime alerts"
            ),
            howItWorks = "allConnect connects through local LAN API / Matter. Displays live air quality index and temp controls.",
            defaultProtocol = ConnectionProtocol.WIFI_LOCAL,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Living Room",
            samplePrimaryLabel = "Air Quality",
            samplePrimaryValue = "Clean (VOC: 45 ppb)",
            sampleSecondaryLabel = "Target Temp",
            sampleSecondaryValue = "72.0°F (Auto)",
            pairingInstructions = "On Ecobee screen tap Menu > General > Wi-Fi > Connect to allConnect Hub."
        ),

        // Smart Plugs & Power Energy
        DeviceCatalogItem(
            id = "tp-link-tapo-p110",
            name = "TP-Link Tapo P110 Smart Plug",
            modelName = "Tapo P110 (EU/US/UK)",
            manufacturer = "TP-Link Systems",
            manufacturerCountry = "Singapore / USA",
            category = DeviceCategory.POWER_ENERGY,
            devicePurpose = "Compact smart plug with real-time power consumption monitoring, timer schedules, and overload auto-cutoff protection.",
            keyCapabilities = listOf(
                "Live Wattage (W) and Daily/Monthly kWh energy meter",
                "Schedule & countdown timer for appliance control",
                "Away mode randomly turns appliances on/off for security",
                "Flame-retardant casing with 15A rating"
            ),
            howItWorks = "Communicates over local Wi-Fi port 9999 (KLAP/AES protocol). allConnect switches power on/off with zero lag and renders live power charts without opening the Tapo app.",
            defaultProtocol = ConnectionProtocol.WIFI_LOCAL,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Kitchen",
            samplePrimaryLabel = "Current Draw",
            samplePrimaryValue = "142.5 W (Espresso Maker)",
            sampleSecondaryLabel = "Energy Today",
            sampleSecondaryValue = "0.84 kWh ($0.11 est.)",
            pairingInstructions = "Plug into wall socket. Hold power button 5s until LED blinks amber and blue."
        ),
        DeviceCatalogItem(
            id = "eve-energy-matter",
            name = "Eve Energy Smart Plug (Matter)",
            modelName = "Eve 20EBO8301",
            manufacturer = "Eve Systems (ABB)",
            manufacturerCountry = "Germany",
            category = DeviceCategory.POWER_ENERGY,
            devicePurpose = "Matter-over-Thread smart plug with ultra-fast responsiveness and precision energy monitoring.",
            keyCapabilities = listOf(
                "Matter over Thread cutting-edge low-power mesh",
                "No cloud, no registration, 100% private local operation",
                "Precise power consumption telemetry",
                "Acts as Thread Router node extending home mesh"
            ),
            howItWorks = "Binds through Matter over Thread. allConnect sends instantaneous relay commands.",
            defaultProtocol = ConnectionProtocol.MATTER_THREAD,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Home Office",
            samplePrimaryLabel = "Active Power",
            samplePrimaryValue = "68.2 W (Desktop PC)",
            sampleSecondaryLabel = "Thread Mesh",
            sampleSecondaryValue = "Signal: -48 dBm (Strong)",
            pairingInstructions = "Scan Matter QR code on side of plug using allConnect camera or enter manual code."
        ),

        // Smart Lighting
        DeviceCatalogItem(
            id = "philips-hue-color-bulb",
            name = "Philips Hue White & Color Ambiance",
            modelName = "LCT025 (E26/E27)",
            manufacturer = "Signify (Philips Hue)",
            manufacturerCountry = "Netherlands",
            category = DeviceCategory.LIGHTING,
            devicePurpose = "16 million color smart LED bulb with tunable white spectrum (2000K-6500K) and 1100-lumen output.",
            keyCapabilities = listOf(
                "16 million rich colors + warm-to-cool white light",
                "Bluetooth direct control or Zigbee mesh connection",
                "Smooth flicker-free dimming from 1% to 100%",
                "Music & screen sync capable"
            ),
            howItWorks = "Connects either directly via BLE or routed through your Hue/Matter Bridge. allConnect provides full color wheel and brightness sliders.",
            defaultProtocol = ConnectionProtocol.BLE,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Master Bedroom",
            samplePrimaryLabel = "Brightness & Color",
            samplePrimaryValue = "80% • Warm Sunset Gold",
            sampleSecondaryLabel = "Power Consumption",
            sampleSecondaryValue = "7.8 W (LED)",
            pairingInstructions = "Turn wall switch off and on 3 times to put bulb into BLE discovery mode."
        ),
        DeviceCatalogItem(
            id = "nanoleaf-shapes-hexagons",
            name = "Nanoleaf Shapes (Hexagons)",
            modelName = "NL42 Hexagon 9PK",
            manufacturer = "Nanoleaf",
            manufacturerCountry = "Canada",
            category = DeviceCategory.LIGHTING,
            devicePurpose = "Modular smart LED light panels with touch reactivity, screen mirroring, and built-in Thread Border Router.",
            keyCapabilities = listOf(
                "Touch-reactive panels turn into interactive lighting canvas",
                "Built-in Thread Border Router for your smart home",
                "Rhythm Music Visualizer responds to ambient sound",
                "Matter certified for effortless ecosystem integration"
            ),
            howItWorks = "Discovered over Wi-Fi and Matter. allConnect toggles scenes, brightness, and reactive effects.",
            defaultProtocol = ConnectionProtocol.WIFI_LOCAL,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Living Room",
            samplePrimaryLabel = "Active Scene",
            samplePrimaryValue = "Northern Lights (Animated)",
            sampleSecondaryLabel = "Panels Active",
            sampleSecondaryValue = "9 of 9 Hexagons",
            pairingInstructions = "Hold Power button for 7 seconds on controller unit until LEDs cycle."
        ),

        // Audio & Media
        DeviceCatalogItem(
            id = "sonos-era-100",
            name = "Sonos Era 100",
            modelName = "S39 (Era 100)",
            manufacturer = "Sonos Inc.",
            manufacturerCountry = "USA",
            category = DeviceCategory.AUDIO_MEDIA,
            devicePurpose = "Next-generation smart stereo speaker with acoustic dual-tweeters, Trueplay tuning, and multi-room audio sync.",
            keyCapabilities = listOf(
                "Stereo sound separation from a single compact speaker",
                "Trueplay room tuning analyzes acoustic reflections",
                "AirPlay 2, Bluetooth 5.0, and Wi-Fi streaming",
                "Groupable with whole-home audio zones"
            ),
            howItWorks = "Discovered via UPnP / Sonos SSDP protocol over local Wi-Fi. allConnect gives you instant volume control, play/pause, and group playback without launching the Sonos S2 app.",
            defaultProtocol = ConnectionProtocol.WIFI_LOCAL,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Living Room",
            samplePrimaryLabel = "Volume Level",
            samplePrimaryValue = "38% (Normal)",
            sampleSecondaryLabel = "Playback State",
            sampleSecondaryValue = "Playing • Lo-Fi Lounge",
            pairingInstructions = "Ensure Sonos speaker is on the same Wi-Fi network. allConnect auto-detects it."
        ),

        // Health & Bio
        DeviceCatalogItem(
            id = "withings-body-scan",
            name = "Withings Body Scan Smart Scale",
            modelName = "WBS08 (6-Lead ECG)",
            manufacturer = "Withings",
            manufacturerCountry = "France",
            category = DeviceCategory.HEALTH,
            devicePurpose = "Medical-grade smart scale with retractable handle measuring segmental body composition, 6-lead ECG, and nerve health score.",
            keyCapabilities = listOf(
                "Segmental fat & muscle mass (arms, legs, torso)",
                "6-Lead ECG to identify vascular stiffness and arrhythmia",
                "Nerve Health Score via sweat gland electrochemical conductance",
                "Multi-user recognition up to 8 profiles"
            ),
            howItWorks = "Syncs biometric weight, fat %, and ECG reports over BLE and local Wi-Fi directly to allConnect.",
            defaultProtocol = ConnectionProtocol.BLE,
            defaultBatteryPowered = true,
            typicalBatteryPct = 95,
            defaultRoom = "Bathroom",
            samplePrimaryLabel = "Latest Weight",
            samplePrimaryValue = "168.4 lbs (Fat: 14.8%)",
            sampleSecondaryLabel = "Vascular Health",
            sampleSecondaryValue = "Optimal (PWV: 6.2 m/s)",
            pairingInstructions = "Press the button on back of scale for 3 seconds until 'Setup' appears on screen."
        ),

        // Security & Sensors
        DeviceCatalogItem(
            id = "ring-video-doorbell-pro",
            name = "Ring Video Doorbell Pro 2",
            modelName = "Ring Pro 2 (Hardwired)",
            manufacturer = "Ring (Amazon)",
            manufacturerCountry = "USA",
            category = DeviceCategory.SECURITY_SENSOR,
            devicePurpose = "1536p HD head-to-toe video doorbell with 3D radar motion detection and Bird's Eye View coverage.",
            keyCapabilities = listOf(
                "1536p HD Head-to-Toe video with color night vision",
                "3D Motion Detection with radar distance measurement",
                "Two-way talk with noise cancellation",
                "Pre-roll 6-second video recording buffer"
            ),
            howItWorks = "Communicates over local RTSP / Wi-Fi stream and local chime notifications. allConnect triggers live alerts and doorbell press chimes.",
            defaultProtocol = ConnectionProtocol.WIFI_LOCAL,
            defaultBatteryPowered = false,
            typicalBatteryPct = -1,
            defaultRoom = "Front Porch",
            samplePrimaryLabel = "Doorbell Status",
            samplePrimaryValue = "Monitoring • Armed",
            sampleSecondaryLabel = "Last Motion",
            sampleSecondaryValue = "12 mins ago (Front Yard)",
            pairingInstructions = "Press and release the small button on side of Ring Doorbell to enter Wi-Fi setup mode."
        ),
        DeviceCatalogItem(
            id = "aqara-door-sensor",
            name = "Aqara Door & Window Sensor T1",
            modelName = "DW-S03 (Zigbee 3.0)",
            manufacturer = "Aqara (Lumi United)",
            manufacturerCountry = "China",
            category = DeviceCategory.SECURITY_SENSOR,
            devicePurpose = "Ultra-compact magnetic contact sensor that detects whether doors, windows, or cabinets are open or closed.",
            keyCapabilities = listOf(
                "Sub-second contact state detection (Open / Closed)",
                "2+ year battery life on a single CR1632 coin cell",
                "22mm maximum magnetic sensing distance",
                "Triggers automated lighting or security alarms"
            ),
            howItWorks = "Broadcasts contact state via Zigbee / Matter hub. allConnect updates open/closed status instantaneously.",
            defaultProtocol = ConnectionProtocol.ZIGBEE_BRIDGE,
            defaultBatteryPowered = true,
            typicalBatteryPct = 90,
            defaultRoom = "Front Door",
            samplePrimaryLabel = "Contact State",
            samplePrimaryValue = "Closed & Secured",
            sampleSecondaryLabel = "Tamper Status",
            sampleSecondaryValue = "Normal • Batt: 90%",
            pairingInstructions = "Press the reset button on sensor with pin for 5 seconds until blue LED flashes 3 times."
        )
    )

    fun getInitialDevices(): List<DeviceEntity> {
        val hueNodesJson = """
            [
              {"id": "hue-1", "name": "Living Room Downlights", "type": "light", "isOn": true, "brightness": 85, "colorHex": "#FFE082", "room": "Living Room"},
              {"id": "hue-2", "name": "TV Ambient Lightstrip", "type": "light", "isOn": true, "brightness": 70, "colorHex": "#00E5FF", "room": "Living Room"},
              {"id": "hue-3", "name": "Sofa Reading Lamp", "type": "light", "isOn": false, "brightness": 45, "colorHex": "#FFB74D", "room": "Living Room"},
              {"id": "hue-4", "name": "Kitchen Island Spots", "type": "light", "isOn": true, "brightness": 100, "colorHex": "#FFFFFF", "room": "Kitchen"},
              {"id": "hue-5", "name": "Dining Room Chandelier", "type": "light", "isOn": true, "brightness": 60, "colorHex": "#FFD54F", "room": "Dining Room"},
              {"id": "hue-6", "name": "Hallway Motion Sensor", "type": "sensor", "isOn": true, "status": "Clear • Battery 95%", "room": "Hallway"}
            ]
        """.trimIndent()

        return listOf(
            DeviceEntity(
                id = "dev-fitbit-charge6",
                name = "My Fitbit Charge 6",
                modelName = "Fitbit Charge 6 (FB423)",
                manufacturer = "Fitbit (Google)",
                manufacturerCountry = "USA",
                category = DeviceCategory.WEARABLE,
                devicePurpose = "Universal Fitness & Heart-Rate Activity Tracker. Designed for 24/7 health monitoring, GPS workout tracking, sleep score analysis, and stress management.",
                keyCapabilities = "Continuous 24/7 Heart Rate & EDA Stress Scan, Built-in GPS for pace & real-time distance, Sleep Stages breakdown & SpO2 blood oxygen, Water resistant up to 50 meters",
                howItWorks = "Directly connects via Bluetooth Low Energy (BLE) GATT profile. allConnect streams live step counts, active heart rate, and syncs sleep metrics directly to your device without requiring the proprietary Fitbit companion app.",
                connectionProtocol = ConnectionProtocol.BLE,
                macAddress = "E4:5F:01:89:BC:42",
                ipAddress = "192.168.1.18",
                room = "Wearables / On Person",
                isOnline = true,
                isFavorite = true,
                signalRssi = -52,
                batteryPercent = 89,
                firmwareVersion = "v1.194.29",
                lastSyncTimestamp = System.currentTimeMillis() - 120_000,
                powerState = true,
                primaryMetricLabel = "Steps Today",
                primaryMetricValue = "8,742 steps",
                secondaryMetricLabel = "Heart Rate",
                secondaryMetricValue = "72 bpm (Resting: 60)",
                customNotes = "Synced daily workout goal (10,000 steps). Battery lasts ~6 days.",
                heartRateBpm = 72,
                restingHeartRateBpm = 60,
                caloriesBurned = 648,
                calorieGoal = 2200,
                stepCount = 8742,
                stepGoal = 10000,
                distanceKm = 6.2f,
                activeMinutes = 45,
                sleepHours = 7.6f
            ),
            DeviceEntity(
                id = "dev-balco-tracker",
                name = "Balco Fitness Tracker",
                modelName = "Balco FT-880 OLED Heart Rate & Pedometer",
                manufacturer = "Balco Lifestyle",
                manufacturerCountry = "Australia / Germany",
                category = DeviceCategory.WEARABLE,
                devicePurpose = "Bluetooth activity & biometric band with optical heart rate monitor, pedometer, calorie burn tracking, and sleep stages.",
                keyCapabilities = "Dynamic Optical PPG Heart Rate (BPM), Step Pedometer & Daily Calorie Burn Estimator, Distance in km/miles, OLED Tap Display, IP67 Splash & Sweat Resistance",
                howItWorks = "Queries BLE GATT 0x180D (Heart Rate Service) and 0x1814 (Activity Pedometer) over Bluetooth. allConnect extracts your current heart rate, calories burned, and steps in real-time.",
                connectionProtocol = ConnectionProtocol.BLE,
                macAddress = "F8:22:98:C1:4E:99",
                ipAddress = "192.168.1.22",
                room = "Wearables / On Person",
                isOnline = true,
                isFavorite = true,
                signalRssi = -58,
                batteryPercent = 94,
                firmwareVersion = "v2.0.4",
                lastSyncTimestamp = System.currentTimeMillis() - 60_000,
                powerState = true,
                primaryMetricLabel = "Heart Rate & Calories",
                primaryMetricValue = "76 bpm • 485 kcal",
                secondaryMetricLabel = "Steps Today",
                secondaryMetricValue = "7,350 / 10,000 steps",
                customNotes = "Real-time optical PPG sensor synced. Fast BLE reconnection active.",
                heartRateBpm = 76,
                restingHeartRateBpm = 62,
                caloriesBurned = 485,
                calorieGoal = 2000,
                stepCount = 7350,
                stepGoal = 10000,
                distanceKm = 5.1f,
                activeMinutes = 34,
                sleepHours = 7.1f
            ),
            DeviceEntity(
                id = "dev-hue-bridge",
                name = "Living Room Hue Bridge",
                modelName = "Philips Hue Bridge 2.1 (BSB002)",
                manufacturer = "Signify (Philips Hue)",
                manufacturerCountry = "Netherlands",
                category = DeviceCategory.SMART_HUB,
                devicePurpose = "Central smart lighting hub that creates a secure local Zigbee 3.0 mesh network for up to 50 smart bulbs and 12 accessories.",
                keyCapabilities = "Local Zigbee 3.0 Mesh with sub-second latency, Matter controller enabled for cross-platform linking, Dynamic lighting scenes & schedules, 100% offline local LAN operation",
                howItWorks = "allConnect discovers the Hue Bridge instantly via local network mDNS / SSDP HTTP API. One tap establishes encrypted local control—no cloud account or separate Philips Hue app required.",
                connectionProtocol = ConnectionProtocol.ZIGBEE_BRIDGE,
                macAddress = "00:17:88:6B:43:A1",
                ipAddress = "192.168.1.120",
                room = "Living Room",
                isOnline = true,
                isFavorite = true,
                signalRssi = -42,
                batteryPercent = -1,
                firmwareVersion = "v1.58.1958",
                lastSyncTimestamp = System.currentTimeMillis() - 45_000,
                powerState = true,
                primaryMetricLabel = "Connected Lights",
                primaryMetricValue = "5 Lights Active • 1 Sensor",
                secondaryMetricLabel = "Mesh Network",
                secondaryMetricValue = "Zigbee Ch 25 (Strong)",
                customNotes = "Controls ceiling downlights, TV gradient strip, sofa reading lamp, and kitchen spotlights.",
                brightnessPercent = 85,
                colorHex = "#FFE082",
                activeScene = "Warm Daylight",
                hubChildNodesJson = hueNodesJson
            ),
            DeviceEntity(
                id = "dev-nest-thermostat",
                name = "Hallway Nest Thermostat",
                modelName = "Google Nest Learning Thermostat 4th Gen",
                manufacturer = "Google Nest",
                manufacturerCountry = "USA",
                category = DeviceCategory.CLIMATE,
                devicePurpose = "Smart programmable thermostat that optimizes heating/cooling energy, detects home/away presence, and balances indoor humidity.",
                keyCapabilities = "Matter-certified local climate control, Farsight borderless glass display, HVAC health monitoring and filter change alerts, Auto-Schedule temperature routines",
                howItWorks = "Communicates over local Wi-Fi / Matter. allConnect allows adjusting temperature setpoints, toggling Eco mode, and reading ambient humidity in real-time.",
                connectionProtocol = ConnectionProtocol.WIFI_LOCAL,
                macAddress = "64:16:66:3F:82:19",
                ipAddress = "192.168.1.144",
                room = "Hallway",
                isOnline = true,
                isFavorite = true,
                signalRssi = -58,
                batteryPercent = -1,
                firmwareVersion = "v2.11.4",
                lastSyncTimestamp = System.currentTimeMillis() - 180_000,
                powerState = true,
                primaryMetricLabel = "Target Temp",
                primaryMetricValue = "71.0°F (Heat)",
                secondaryMetricLabel = "Ambient Climate",
                secondaryMetricValue = "70.2°F • 44% RH",
                customNotes = "Eco temperature set to 64°F when away.",
                targetTemperature = 71.0f,
                climateMode = "HEAT",
                fanSpeed = "AUTO"
            ),
            DeviceEntity(
                id = "dev-tapo-plug",
                name = "Coffee Station Smart Plug",
                modelName = "TP-Link Tapo P110",
                manufacturer = "TP-Link Systems",
                manufacturerCountry = "Singapore / USA",
                category = DeviceCategory.POWER_ENERGY,
                devicePurpose = "Compact smart plug with real-time power consumption monitoring, timer schedules, and overload auto-cutoff protection.",
                keyCapabilities = "Live Wattage (W) and Daily/Monthly kWh energy meter, Schedule & countdown timer, Away mode security timers, Flame-retardant casing (15A)",
                howItWorks = "Communicates over local Wi-Fi via AES encryption. allConnect switches power on/off with zero lag and renders live power consumption without opening the Tapo app.",
                connectionProtocol = ConnectionProtocol.WIFI_LOCAL,
                macAddress = "D8:0D:17:9A:3C:7F",
                ipAddress = "192.168.1.189",
                room = "Kitchen",
                isOnline = true,
                isFavorite = false,
                signalRssi = -64,
                batteryPercent = -1,
                firmwareVersion = "v1.2.3",
                lastSyncTimestamp = System.currentTimeMillis() - 30_000,
                powerState = true,
                primaryMetricLabel = "Current Draw",
                primaryMetricValue = "138.4 W (Brewing)",
                secondaryMetricLabel = "Energy Today",
                secondaryMetricValue = "0.72 kWh ($0.09)",
                customNotes = "Auto turns off after 45 minutes of inactivity."
            ),
            DeviceEntity(
                id = "dev-sonos-speaker",
                name = "Living Room Sonos Era",
                modelName = "Sonos Era 100 (S39)",
                manufacturer = "Sonos Inc.",
                manufacturerCountry = "USA",
                category = DeviceCategory.AUDIO_MEDIA,
                devicePurpose = "Next-generation smart stereo speaker with acoustic dual-tweeters, Trueplay tuning, and multi-room audio sync.",
                keyCapabilities = "Stereo sound separation from a single compact speaker, Trueplay room acoustic tuning, AirPlay 2, Bluetooth & Wi-Fi streaming, Multi-room speaker grouping",
                howItWorks = "Discovered via UPnP / Sonos SSDP protocol over local Wi-Fi. allConnect gives you instant volume control, play/pause, and group playback without launching the Sonos S2 app.",
                connectionProtocol = ConnectionProtocol.WIFI_LOCAL,
                macAddress = "94:9F:3E:21:5C:8B",
                ipAddress = "192.168.1.205",
                room = "Living Room",
                isOnline = true,
                isFavorite = false,
                signalRssi = -49,
                batteryPercent = -1,
                firmwareVersion = "v16.1.2",
                lastSyncTimestamp = System.currentTimeMillis() - 10_000,
                powerState = true,
                primaryMetricLabel = "Volume Level",
                primaryMetricValue = "42% (Normal)",
                secondaryMetricLabel = "Playback State",
                secondaryMetricValue = "Playing • Chill Wave",
                customNotes = "Linked to Living Room TV zone.",
                volumePercent = 42,
                playbackState = "Playing",
                currentTrack = "Lo-Fi Lounge • Ambient Radio",
                isMuted = false
            )
        )
    }

    // Nearby simulated/live scan candidates for Discovery radar
    fun getDiscoveredCandidates(): List<DeviceCatalogItem> {
        return catalog.filter { item ->
            // Exclude already added ones by default for scanner demo
            item.id !in listOf("fitbit-charge-6", "philips-hue-bridge", "google-nest-thermostat", "tp-link-tapo-p110", "sonos-era-100")
        }
    }

    fun findMatchingCatalog(searchQuery: String): List<DeviceCatalogItem> {
        if (searchQuery.isBlank()) return catalog
        val query = searchQuery.trim().lowercase()
        return catalog.filter {
            it.name.lowercase().contains(query) ||
            it.manufacturer.lowercase().contains(query) ||
            it.category.displayName.lowercase().contains(query) ||
            it.devicePurpose.lowercase().contains(query)
        }
    }

    fun getInitialFeatureRequests(): List<com.example.data.model.FeatureRequestEntity> {
        return listOf(
            com.example.data.model.FeatureRequestEntity(
                id = "req-sleep-trigger",
                title = "Smart Home Sleep Mode Triggered by Fitbit / Wearable Biometrics",
                rawUserThought = "I want my Philips Hue lights to automatically turn off and my Nest thermostat to switch to eco night temperature the moment my Fitbit or Apple Watch detects I fell asleep, without me having to say anything to Google Assistant or touch my phone.",
                aiSynthesis = "Gemini Analyzed 3,420 community thoughts: Users require biometric-driven cross-device automation. When local BLE heart-rate and accelerometer telemetry indicates sleep onset, AllConnect broadcasts local Zigbee and Matter sleep triggers to dim lighting and adjust climate.",
                technicalFeasibility = "High • Local BLE GATT Sleep Characteristic + Matter 1.2 OnOff Cluster",
                protocolsInvolved = "BLE GATT • Zigbee 3.0 • Matter",
                category = "Wearables & Automations",
                requestVotes = 3420,
                userHasVoted = true,
                status = com.example.data.model.FeatureStatus.ADDED_TO_ALLCONNECT,
                gatherCycleMonth = "Cycle 8 (Current 30-Day Window)",
                submittedTimestamp = System.currentTimeMillis() - (18L * 24 * 60 * 60 * 1000),
                isWinningFeature = true,
                engineeringNotes = "Approved by AllConnect Core Team after 30-day community poll! Full bidirectional local rule engine implemented."
            ),
            com.example.data.model.FeatureRequestEntity(
                id = "req-matter-energy",
                title = "Universal Matter 1.3 Real-Time Energy & Solar Generation Dashboard",
                rawUserThought = "Can AllConnect show live power draw curves for all my TP-Link Tapo plugs and Eve Energy smart plugs combined in one single live graph, and alert me when power spikes?",
                aiSynthesis = "Gemini Analyzed 2,890 thoughts: Aggregation of Matter 1.3 electrical measurement cluster attributes. Generates live watt usage curves and calculates electricity cost savings locally without sending data to vendor clouds.",
                technicalFeasibility = "High • Matter Electrical Measurement & Energy EV Clusters",
                protocolsInvolved = "Matter 1.3 • Thread",
                category = "Energy & Smart Plugs",
                requestVotes = 2890,
                userHasVoted = false,
                status = com.example.data.model.FeatureStatus.IN_DEVELOPMENT,
                gatherCycleMonth = "Cycle 8 (Current 30-Day Window)",
                submittedTimestamp = System.currentTimeMillis() - (14L * 24 * 60 * 60 * 1000),
                isWinningFeature = false,
                engineeringNotes = "Currently in staging sprint. Live wattage telemetry aggregator tested on 12 smart plug models."
            ),
            com.example.data.model.FeatureRequestEntity(
                id = "req-rogue-shield",
                title = "AirTag & Rogue BLE Tracker Proactive Detection Shield",
                rawUserThought = "I want AllConnect to continuously scan in background and warn me with a loud alert if an unknown Bluetooth tracker or suspicious BLE beacon has been moving with me for more than 15 minutes.",
                aiSynthesis = "Gemini Analyzed 2,150 thoughts: Background BLE RSSI proximity correlation. Detects unbonded rotating MAC addresses following the user over temporal delta intervals, providing anti-stalking alerts.",
                technicalFeasibility = "Medium • Background BLE Scan + RSSI Spatial Delta Filter",
                protocolsInvolved = "BLE Advertising Payloads • FindMy Protocol",
                category = "Security & Warnings",
                requestVotes = 2150,
                userHasVoted = false,
                status = com.example.data.model.FeatureStatus.GATHERING,
                gatherCycleMonth = "Cycle 8 (Current 30-Day Window)",
                submittedTimestamp = System.currentTimeMillis() - (8L * 24 * 60 * 60 * 1000),
                isWinningFeature = false,
                engineeringNotes = "Community voting active. Ranked #3 in current 30-day incubation cycle."
            ),
            com.example.data.model.FeatureRequestEntity(
                id = "req-ai-manuals",
                title = "Interactive Gemini AI Q&A over Any Device's PDF Manual",
                rawUserThought = "When I look at the manual for my smart switch or thermostat in AllConnect, I want to be able to ask questions like 'how do I wire this with 3-way lighting?' and have Gemini answer directly from the official schematic.",
                aiSynthesis = "Gemini Analyzed 1,840 thoughts: Contextual grounded document QA. Ingests retrieved PDF user manuals and schematics to give step-by-step wiring instructions and troubleshooting advice in plain English.",
                technicalFeasibility = "High • Gemini 3.5 Flash Multimodal Document Grounding",
                protocolsInvolved = "REST API • Gemini AI Engine",
                category = "Hardware Manuals & AI",
                requestVotes = 1840,
                userHasVoted = false,
                status = com.example.data.model.FeatureStatus.GATHERING,
                gatherCycleMonth = "Cycle 8 (Current 30-Day Window)",
                submittedTimestamp = System.currentTimeMillis() - (5L * 24 * 60 * 60 * 1000),
                isWinningFeature = false,
                engineeringNotes = "Community voting active. High user engagement."
            ),
            com.example.data.model.FeatureRequestEntity(
                id = "req-ant-plus",
                title = "Universal Garmin & Apple Watch Heart Rate Relay to Peloton / Gym Gear",
                rawUserThought = "Let me broadcast my Garmin or Apple Watch live heart rate over BLE Heart Rate Profile directly into gym treadmills and Peloton without needing a separate chest strap.",
                aiSynthesis = "Gemini Analyzed 1,620 thoughts: Emulate standard BLE GATT Heart Rate Service (0x180D) using phone as a transparent peripheral relay, bridging proprietary smartwatch sensors to commercial fitness equipment.",
                technicalFeasibility = "High • Android BluetoothLeAdvertiser + 0x180D Service",
                protocolsInvolved = "BLE GATT Peripheral & Central",
                category = "Wearables & Fitness",
                requestVotes = 1620,
                userHasVoted = false,
                status = com.example.data.model.FeatureStatus.GATHERING,
                gatherCycleMonth = "Cycle 8 (Current 30-Day Window)",
                submittedTimestamp = System.currentTimeMillis() - (2L * 24 * 60 * 60 * 1000),
                isWinningFeature = false,
                engineeringNotes = "Community voting active."
            )
        )
    }
}

