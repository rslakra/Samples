# React Native Test App

A complete, self-contained React Native mobile testing solution with an integrated Node.js backend. Test and monitor API endpoints from iOS and Android devices with a beautiful modern UI.

> **Self-Contained Project**: This project includes both the backend server and mobile app in one repository. No external dependencies needed!

## Features

- 🏠 **Home/Dashboard** - Application info and quick navigation to all endpoints
- 🏥 **Health Check** - Real-time server health monitoring with auto-refresh (every 5 seconds)
- 📊 **System Information** - Node.js version, memory usage, platform details
- 🧪 **Test Endpoint** - Interactive testing with path parameters and query strings
- 🔄 **Echo Test** - POST endpoint testing with JSON data
- 🎨 **Modern UI** - Beautiful gradient design matching the web dashboard
- 📱 **Native Navigation** - Smooth screen transitions
- 🔄 **Pull to Refresh** - Easy data refresh on all screens

## Project Structure

```
testapp-reactnative/
├── App.js                      # Main navigation setup
├── index.js                    # Entry point
├── package.json                # Dependencies
├── ios/                        # iOS native project
├── android/                    # Android native project
├── backend/                    # Node.js backend server
│   ├── server.js               # Express server
│   ├── package.json            # Backend dependencies
│   ├── public/                 # Web dashboard
│   └── README.md               # Backend documentation
└── src/
    ├── components/             # Reusable UI components
    │   ├── Button.js
    │   ├── Card.js
    │   ├── DataContainer.js
    │   ├── Input.js
    │   └── StatusIndicator.js
    ├── constants/
    │   └── colors.js           # App color scheme
    ├── screens/                # 5 main screens
    │   ├── HomeScreen.js
    │   ├── HealthScreen.js
    │   ├── SystemInfoScreen.js
    │   ├── TestScreen.js
    │   └── EchoScreen.js
    └── services/
        └── api.js              # API client (Axios)
```

---

## Prerequisites

- **Node.js** >= 18.0.0
- **npm** or **yarn**
- **For iOS**: macOS with Xcode installed
- **For Android**: Android Studio with SDK and emulator

> **Note**: For React Native environment setup, visit: https://reactnative.dev/docs/environment-setup

---

## Quick Start

**TLDR:** Start backend → Configure URL → Run mobile app

### Terminal 1 - Start Backend
```bash
cd testapp-reactnative/backend
npm install    # First time only
npm start
```

### Terminal 2 - Run Mobile App
```bash
cd testapp-reactnative
npm install # First time only
# cd android && export JAVA_HOME=$(/usr/libexec/java_home -v 21) && npm run android
npm run android # Android
OR
npm run ios     # iOS
```

**Important Steps:**

1. **Configure Backend URL** - Edit `app-config.json`:
   ```json
   "backend": {
     "defaultUrl": "http://localhost:4000"  // Change based on platform
   }
   ```
   - iOS Simulator: `http://localhost:4000` (default)
   - Android Emulator: `http://10.0.2.2:4000`
   - Physical Device: `http://YOUR_IP:4000` (find with `ifconfig` or `ipconfig`)

2. **iOS Only** - Install CocoaPods dependencies:
   ```bash
   brew install cocoapods  # if not installed
   cd testapp-reactnative/ios && pod install && cd ..
   ```

---

## Detailed Setup (Step-by-Step)

### Step 1: Start the Backend Server (Required First!)

The React Native app depends on the backend API, so start it first.

**Open Terminal 1** and run:

```bash
cd testapp-reactnative/backend
npm install  # (only needed first time)
npm start
```

You should see: `Server running on http://0.0.0.0:4000`

**Keep this terminal running!**

### Step 2: Install React Native Dependencies

**Open Terminal 2** and run:

```bash
cd testapp-reactnative
npm install
```

### Step 3: Configure Backend URL (Important!)

Edit the backend URL based on your platform **before** running the app.

**File:** `testapp-reactnative/app-config.json`

```json
{
  "backend": {
    "defaultUrl": "http://localhost:4000"
  }
}
```

**Change `defaultUrl` based on your platform:**
- **iOS Simulator**: `http://localhost:4000` (default - no change needed)
- **Android Emulator**: `http://10.0.2.2:4000`
- **Physical Device**: `http://YOUR_IP:4000` (e.g., `http://192.168.1.100:4000`)

To find your computer's IP address:
- **macOS/Linux**: Run `ifconfig | grep inet` or `hostname -I`
- **Windows**: Run `ipconfig` and look for IPv4 Address

### Step 4: Platform-Specific Setup

#### For iOS (macOS only):

**a) Install CocoaPods** (if not already installed):

```bash
# Using Homebrew (recommended)
brew install cocoapods

# OR using RubyGems (requires Ruby 3.1+)
sudo gem install cocoapods
```

**b) Install iOS dependencies:**

```bash
cd testapp-reactnative/ios
pod install
cd ..
```

#### For Android:

- Ensure Android Studio is installed
- Install Android SDK Platform (API 33+)
- Create or start an Android Virtual Device (AVD) in Android Studio
- Or connect a physical Android device with USB debugging enabled

### Step 5: Run the React Native App

Still in **Terminal 2** (backend is running in Terminal 1):

#### For iOS:

```bash
cd testapp-reactnative
npm run ios
```

Or to specify a simulator:
```bash
npx react-native run-ios --simulator="iPhone 15"
```

#### For Android:

```bash
cd testapp-reactnative
npm run android
```

### Step 6: Verify the Connection

Once the app launches:

1. **Home Screen**: Should show server health status (🟢 green = connected)
2. **Tap "Health Check"**: Verify real-time data loads from backend
3. **Tap "System Info"**: View server metrics (Node.js version, memory, etc.)
4. **Tap "Test Endpoint"**: Try quick test buttons (hello, world, awesome)
5. **Tap "Echo Test"**: Send a test message and see it echo back with timestamp

---

## API Endpoints

The app connects to these backend endpoints:

| Endpoint | Method | Screen | Description |
|----------|--------|--------|-------------|
| `/api` | GET | Home | Application info and available endpoints |
| `/health` | GET | Health Check | Server health status |
| `/info` | GET | System Info | Node.js version, memory, platform details |
| `/test/:message` | GET | Test Endpoint | Test with path parameter and format query |
| `/echo` | POST | Echo Test | Echo back JSON data with timestamp |

---

## Development Commands

```bash
# Start Metro bundler
cd testapp-reactnative
npm start

# Run on iOS
npm run ios

# Run on Android
npm run android

# Clear Metro cache
npm start -- --reset-cache

# Run on specific iOS simulator
npx react-native run-ios --simulator="iPhone 15 Pro"
```

### Debug Menu

- **iOS**: Press `Cmd + D` in simulator
- **Android**: Press `Cmd + M` or shake device

Debug menu options:
- Reload app
- Enable Hot Reloading
- Toggle Inspector
- Performance Monitor

---

## Testing

### Basic Testing Workflow

**Terminal 1** - Start Backend:
```bash
cd testapp-reactnative/backend
npm install  # (only needed first time)
npm start
```

**Terminal 2** - Start Mobile App:
```bash
cd testapp-reactnative
npm run ios     # or npm run android
```

**Verify Connection:**
- Check home screen shows green status indicator
- Tap through each screen to verify data loads
- Test auto-refresh in Health Check screen
- Try sending custom messages in Test Endpoint
- Test POST requests in Echo Test screen

### Testing All Features

1. **Home Screen**
   - Verify server status indicator (green = healthy)
   - Tap each endpoint card to navigate
   - Pull down to refresh

2. **Health Check Screen**
   - Enable auto-refresh toggle
   - Watch data update every 5 seconds
   - Verify timestamp updates
   - Pull down to manually refresh

3. **System Info Screen**
   - Check Node.js version displays
   - Verify memory usage shows
   - Check platform details
   - View quick stats cards

4. **Test Endpoint Screen**
   - Enter custom message
   - Try quick test buttons (hello, world, awesome)
   - Test with format parameter
   - Verify transformations (uppercase, lowercase, reversed)

5. **Echo Test Screen**
   - Enter custom message
   - Add JSON data (optional)
   - Tap "Fill Example" for sample data
   - Send and verify echo response

### Testing Multiple Clients Simultaneously

Run backend, mobile app, and web dashboard together:

**Terminal 1** - Backend:
```bash
cd testapp-reactnative/backend
npm start
```

**Terminal 2** - Mobile App:
```bash
cd testapp-reactnative
npm run ios     # or npm run android
```

**Browser** - Web Dashboard:
```
Open: http://localhost:4000/index.html
```

**Test Scenarios:**
- Compare response data between mobile and web
- Enable auto-refresh on both clients
- Verify timestamps match
- Test concurrent API calls
- Check server handles multiple clients

### Testing on Physical Devices

1. **Find your computer's IP address:**
   - macOS/Linux: `ifconfig | grep inet`
   - Windows: `ipconfig`

2. **Update backend URL** in `app-config.json`:
   ```json
   {
     "backend": {
       "defaultUrl": "http://YOUR_IP:4000"  // e.g., http://192.168.1.100:4000
     }
   }
   ```

3. **Ensure devices are on the same network** as your computer

4. **Run the app** on your physical device

5. **Verify connection** by checking home screen status indicator

---

## Troubleshooting

### Backend Connection Issues

**Problem:** "Cannot connect to server" / "Network request failed" / Red status indicator

**Solutions:**

1. **Verify backend is running:**
   ```bash
   curl http://localhost:4000/health
   ```
   Should return: `{"status":"healthy",...}`

2. **Check backend URL configuration:**
   - Open `testapp-reactnative/app-config.json`
   - Verify `backend.defaultUrl` matches your platform:
     - iOS Simulator: `http://localhost:4000`
     - Android Emulator: `http://10.0.2.2:4000`
     - Physical Device: `http://YOUR_IP:4000`

3. **Check firewall settings:**
   - Ensure port 4000 is accessible
   - Temporarily disable firewall to test
   - Add firewall rule for port 4000

4. **For physical devices:**
   - Device and computer must be on same Wi-Fi network
   - Use computer's local IP (not localhost)
   - Check router doesn't block device-to-device communication

### iOS Build Issues

**Problem:** "iOS project folder not found"

**Solution:**
```bash
cd testapp-reactnative
# Install CocoaPods if needed
brew install cocoapods
# Install iOS dependencies
cd ios
pod install
cd ..
npm run ios
```

**Problem:** iOS build fails after pod install

**Solution:**
```bash
cd testapp-reactnative/ios
rm -rf Pods Podfile.lock
pod install
cd ..
npm run ios
```

**Problem:** Xcode signing errors

**Solution:**
- Open `testapp-reactnative/ios/TestAppReactNative.xcworkspace` in Xcode
- Select your Apple Developer team in Signing & Capabilities
- Select a provisioning profile
- Try building again

**Problem:** "Command not found: pod"

**Solution:**
```bash
# Install CocoaPods via Homebrew
brew install cocoapods

# Or update Ruby and use gem
gem install cocoapods
```

### Android Build Issues

**Problem:** Android build fails / Gradle errors

**Solution:**
```bash
cd testapp-reactnative/android
./gradlew clean
cd ..
npm run android
```

**Problem:** "Android SDK not found"

**Solution:**
- Install Android Studio
- Open Android Studio → Settings → SDK Manager
- Install Android SDK Platform 33
- Set ANDROID_HOME environment variable

**Problem:** "No emulator found" / "No connected devices"

**Solution:**
- Open Android Studio → AVD Manager
- Create a new Virtual Device
- Start the emulator
- Or connect physical device with USB debugging enabled

### Metro Bundler Issues

**Problem:** "Unable to resolve module" / Module not found errors

**Solution:**
```bash
cd testapp-reactnative
rm -rf node_modules
npm install
npm start -- --reset-cache
```

**Problem:** Metro bundler not starting / stuck

**Solution:**
```bash
cd testapp-reactnative
# Kill any existing Metro processes
pkill -f "react-native"
# Clear cache and restart
npm start -- --reset-cache
```

**Problem:** "Port 8081 already in use"

**Solution:**
```bash
# Kill process on port 8081
lsof -ti:8081 | xargs kill -9
# Or start on different port
npm start -- --port 8082
```

### App Runtime Issues

**Problem:** App crashes on launch

**Solution:**
- Check Metro bundler is running
- Clear cache: `npm start -- --reset-cache`
- Rebuild app completely
- Check console logs for error details

**Problem:** Data not loading / blank screens

**Solution:**
- Verify backend is running on port 4000
- Check backend URL in `src/services/api.js`
- Open debug menu and check console for errors
- Test backend directly: `curl http://localhost:4000/health`

**Problem:** Auto-refresh not working

**Solution:**
- Toggle auto-refresh off and on again
- Check console for JavaScript errors
- Verify app has proper state management
- Reload app: Press R twice (Android) or Cmd+R (iOS)

### General Troubleshooting Steps

1. **Clear everything and start fresh:**
   ```bash
   cd testapp-reactnative
   rm -rf node_modules
   npm install
   npm start -- --reset-cache
   ```

2. **Check versions:**
   ```bash
   node --version    # Should be >= 18.0.0
   npm --version
   npx react-native --version
   ```

3. **View detailed logs:**
   - iOS: Check Xcode console
   - Android: Run `adb logcat`
   - Metro: Check terminal running npm start

4. **Test backend independently:**
   ```bash
   curl http://localhost:4000/health
   curl http://localhost:4000/info
   curl -X POST http://localhost:4000/echo -H "Content-Type: application/json" -d '{"message":"test"}'
   ```

---

## Deployment / Building for Production

### iOS Production Build

```bash
cd testapp-reactnative
# Open project in Xcode
open ios/TestAppReactNative.xcworkspace
```

Then in Xcode:
1. Select your Apple Developer team
2. Configure signing & capabilities
3. Select "Generic iOS Device" or your device
4. Product → Archive
5. Follow the App Store submission wizard

### Android Production Build

```bash
cd testapp-reactnative
# Generate release APK
cd android
./gradlew assembleRelease
cd ..
```

The APK will be at:
```
android/app/build/outputs/apk/release/app-release.apk
```

For a signed release (Play Store):
1. Generate a signing key: `keytool -genkey -v -keystore my-release-key.keystore -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000`
2. Configure `android/app/build.gradle` with keystore details
3. Run: `cd android && ./gradlew bundleRelease`

---

## Technologies Used

- **React Native** 0.73.0 - Cross-platform mobile framework
- **React Navigation** 6.x - Native navigation
- **Axios** 1.6.2 - HTTP client for API requests
- **React Native Safe Area Context** - Safe area handling
- **React Native Screens** - Optimized navigation

---

## License

MIT

---

## Summary

This React Native app provides a **complete self-contained mobile testing solution**:

✅ **Integrated backend** - Node.js server included in `backend/` folder  
✅ **Web dashboard** - Access at `http://localhost:4000/index.html`  
✅ **Mobile app** - Native iOS and Android support  
✅ **Real-time API monitoring** - Auto-refresh and live updates  
✅ **Beautiful, modern UI** - Gradient design with smooth animations  
✅ **Production-ready** - Complete testing solution in one project  

**Quick Start:** Backend first → Configure URL in `app-config.json` → Install dependencies → Run app → Test features

**All-in-one project** - Backend and mobile app in the same repository. Backend URL configured in `app-config.json` for easy platform switching.

For issues, check the **Troubleshooting** section above or ensure the backend is running on port 4000.
