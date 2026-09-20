import React from 'react';
import {View, Text, StyleSheet, Animated} from 'react-native';
import {Colors} from '../constants/colors';

export const StatusIndicator = ({isHealthy, statusText}) => {
  const pulseAnim = React.useRef(new Animated.Value(1)).current;

  React.useEffect(() => {
    if (isHealthy) {
      Animated.loop(
        Animated.sequence([
          Animated.timing(pulseAnim, {
            toValue: 0.5,
            duration: 1000,
            useNativeDriver: true,
          }),
          Animated.timing(pulseAnim, {
            toValue: 1,
            duration: 1000,
            useNativeDriver: true,
          }),
        ])
      ).start();
    }
  }, [isHealthy, pulseAnim]);

  return (
    <View style={styles.container}>
      <Animated.View
        style={[
          styles.indicator,
          {
            backgroundColor: isHealthy ? Colors.success : Colors.error,
            opacity: isHealthy ? pulseAnim : 1,
          },
        ]}
      />
      <Text style={styles.text}>
        {isHealthy ? '🟢' : '🔴'} {statusText}
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 10,
    padding: 15,
    backgroundColor: Colors.background,
    borderRadius: 8,
    marginTop: 15,
  },
  indicator: {
    width: 12,
    height: 12,
    borderRadius: 6,
  },
  text: {
    fontSize: 16,
    color: Colors.text,
    fontWeight: '500',
  },
});

