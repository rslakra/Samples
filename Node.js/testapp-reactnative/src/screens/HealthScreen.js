import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  SafeAreaView,
  RefreshControl,
  Switch,
} from 'react-native';
import {Card, CardHeader, DataContainer, StatusIndicator} from '../components';
import {Colors} from '../constants/colors';
import apiService from '../services/api';

export const HealthScreen = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [timestamp, setTimestamp] = useState(null);
  const [isHealthy, setIsHealthy] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [autoRefresh, setAutoRefresh] = useState(false);

  const fetchData = async () => {
    setLoading(true);
    setError(null);

    const result = await apiService.getHealth();

    if (result.success) {
      setData(result.data);
      setTimestamp(Date.now());
      setIsHealthy(result.data.status === 'healthy');
    } else {
      setError(result.error);
      setIsHealthy(false);
    }

    setLoading(false);
  };

  const onRefresh = async () => {
    setRefreshing(true);
    await fetchData();
    setRefreshing(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    let interval;
    if (autoRefresh) {
      interval = setInterval(() => {
        fetchData();
      }, 5000);
    }
    return () => {
      if (interval) {
        clearInterval(interval);
      }
    };
  }, [autoRefresh]);

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>🏥 Health Check</Text>
        <StatusIndicator
          isHealthy={isHealthy}
          statusText={isHealthy ? 'Server is healthy' : 'Server error or offline'}
        />
      </View>

      <ScrollView
        style={styles.content}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }>
        <Card>
          <CardHeader title="Health Status" icon="🏥" onRefresh={fetchData} />
          <DataContainer
            data={data}
            loading={loading}
            error={error}
            timestamp={timestamp}
          />
        </Card>

        <Card>
          <View style={styles.autoRefreshContainer}>
            <Text style={styles.autoRefreshLabel}>
              Auto-refresh every 5 seconds
            </Text>
            <Switch
              value={autoRefresh}
              onValueChange={setAutoRefresh}
              trackColor={{false: Colors.border, true: Colors.primary}}
              thumbColor={Colors.card}
            />
          </View>
        </Card>

        <Card>
          <CardHeader title="Endpoint Information" icon="📋" />
          <View style={styles.infoContainer}>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Method:</Text>
              <Text style={styles.infoValue}>GET</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Path:</Text>
              <Text style={styles.infoValue}>/health</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Description:</Text>
              <Text style={styles.infoValue}>
                Returns the current health status of the server
              </Text>
            </View>
          </View>
        </Card>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.background,
  },
  header: {
    backgroundColor: Colors.card,
    padding: 20,
    borderBottomLeftRadius: 20,
    borderBottomRightRadius: 20,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 5,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: Colors.primary,
    textAlign: 'center',
    marginBottom: 10,
  },
  content: {
    flex: 1,
    padding: 15,
  },
  autoRefreshContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  autoRefreshLabel: {
    fontSize: 16,
    fontWeight: '600',
    color: Colors.text,
  },
  infoContainer: {
    gap: 15,
  },
  infoRow: {
    flexDirection: 'row',
    paddingVertical: 8,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
  },
  infoLabel: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.text,
    width: 100,
  },
  infoValue: {
    flex: 1,
    fontSize: 14,
    color: Colors.textSecondary,
  },
});

