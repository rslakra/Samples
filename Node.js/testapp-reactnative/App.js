import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {
  HomeScreen,
  HealthScreen,
  SystemInfoScreen,
  TestScreen,
  EchoScreen,
} from './src/screens';
import {Colors} from './src/constants/colors';

const Stack = createNativeStackNavigator();

function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="Home"
        screenOptions={{
          headerStyle: {
            backgroundColor: Colors.primary,
          },
          headerTintColor: Colors.card,
          headerTitleStyle: {
            fontWeight: 'bold',
          },
          headerShadowVisible: true,
          animation: 'slide_from_right',
        }}>
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{
            title: 'Node.js Test App',
            headerShown: false,
          }}
        />
        <Stack.Screen
          name="Health"
          component={HealthScreen}
          options={{
            title: 'Health Check',
          }}
        />
        <Stack.Screen
          name="SystemInfo"
          component={SystemInfoScreen}
          options={{
            title: 'System Information',
          }}
        />
        <Stack.Screen
          name="Test"
          component={TestScreen}
          options={{
            title: 'Test Endpoint',
          }}
        />
        <Stack.Screen
          name="Echo"
          component={EchoScreen}
          options={{
            title: 'Echo Test',
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default App;

