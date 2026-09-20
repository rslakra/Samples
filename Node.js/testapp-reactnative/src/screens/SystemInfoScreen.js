import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  SafeAreaView,
  RefreshControl,
} from 'react-native';
import {Card, CardHeader, DataContainer} from '../components';
import {Colors} from '../constants/colors';
import apiService from '../services/api';

export const SystemInfoScreen = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [timestamp, setTimestamp] = useState(null);
  const [refreshing, setRefreshing] = useState(false);

  const fetchData = async () => {
    setLoading(true);
    setError(null);

    const result = await apiService.getSystemInfo();

    if (result.success) {
      setData(result.data);
      setTimestamp(Date.now());
    } else {
      setError(result.error);
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

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>📊 System Information</Text>
        <Text style={styles.subtitle}>Node.js & Server Metrics</Text>
      </View>

      <ScrollView
        style={styles.content}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }>
        <Card>
          <CardHeader title="System Info" icon="📊" onRefresh={fetchData} />
          <DataContainer
            data={data}
            loading={loading}
            error={error}
            timestamp={timestamp}
          />
        </Card>

        {data?.system && (
          <Card>
            <CardHeader title="Quick Stats" icon="📈" />
            <View style={styles.statsContainer}>
              <View style={styles.statItem}>
                <Text style={styles.statLabel}>Platform</Text>
                <Text style={styles.statValue}>{data.system.platform}</Text>
              </View>
              <View style={styles.statItem}>
                <Text style={styles.statLabel}>Node.js Version</Text>
                <Text style={styles.statValue}>{data.system.nodeVersion}</Text>
              </View>
              <View style={styles.statItem}>
                <Text style={styles.statLabel}>Architecture</Text>
                <Text style={styles.statValue}>{data.system.architecture}</Text>
              </View>
              <View style={styles.statItem}>
                <Text style={styles.statLabel}>Memory Used</Text>
                <Text style={styles.statValue}>
                  {data.system.memory?.used || 'N/A'}
                </Text>
              </View>
              <View style={styles.statItem}>
                <Text style={styles.statLabel}>Uptime</Text>
                <Text style={styles.statValue}>{data.system.uptime}</Text>
              </View>
              <View style={styles.statItem}>
                <Text style={styles.statLabel}>Environment</Text>
                <Text style={styles.statValue}>{data.system.environment}</Text>
              </View>
            </View>
          </Card>
        )}

        <Card>
          <CardHeader title="Endpoint Information" icon="📋" />
          <View style={styles.infoContainer}>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Method:</Text>
              <Text style={styles.infoValue}>GET</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Path:</Text>
              <Text style={styles.infoValue}>/info</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Description:</Text>
              <Text style={styles.infoValue}>
                Returns detailed system information including Node.js version,
                memory usage, platform, and uptime
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
  },
  subtitle: {
    fontSize: 14,
    color: Colors.textSecondary,
    textAlign: 'center',
    marginTop: 5,
  },
  content: {
    flex: 1,
    padding: 15,
  },
  statsContainer: {
    gap: 12,
  },
  statItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 12,
    paddingHorizontal: 15,
    backgroundColor: Colors.background,
    borderRadius: 8,
  },
  statLabel: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.text,
  },
  statValue: {
    fontSize: 14,
    color: Colors.textSecondary,
    fontWeight: '500',
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

