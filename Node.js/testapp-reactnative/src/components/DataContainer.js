import React from 'react';
import {View, Text, StyleSheet, ScrollView} from 'react-native';
import {Colors} from '../constants/colors';

export const DataContainer = ({data, loading, error, timestamp}) => {
  if (loading) {
    return (
      <View style={styles.container}>
        <Text style={styles.loading}>Loading...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.errorContainer}>
        <Text style={styles.errorText}>{error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.wrapper}>
      <ScrollView style={styles.container}>
        <Text style={styles.data}>{JSON.stringify(data, null, 2)}</Text>
      </ScrollView>
      {timestamp && (
        <Text style={styles.timestamp}>
          Last updated: {new Date(timestamp).toLocaleString()}
        </Text>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
  },
  container: {
    backgroundColor: Colors.codeBackground,
    borderRadius: 6,
    padding: 15,
    maxHeight: 400,
  },
  data: {
    fontFamily: 'monospace',
    fontSize: 12,
    color: Colors.text,
  },
  loading: {
    textAlign: 'center',
    padding: 20,
    color: Colors.textSecondary,
  },
  errorContainer: {
    backgroundColor: Colors.errorLight,
    borderRadius: 6,
    padding: 15,
    borderLeftWidth: 4,
    borderLeftColor: Colors.error,
  },
  errorText: {
    color: Colors.error,
    fontSize: 14,
  },
  timestamp: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 10,
    textAlign: 'right',
  },
});

