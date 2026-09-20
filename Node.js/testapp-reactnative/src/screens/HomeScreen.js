import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  RefreshControl,
} from 'react-native';
import {Card, CardHeader, DataContainer, StatusIndicator} from '../components';
import {Colors} from '../constants/colors';
import apiService from '../services/api';

export const HomeScreen = ({navigation}) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [timestamp, setTimestamp] = useState(null);
  const [isHealthy, setIsHealthy] = useState(false);
  const [refreshing, setRefreshing] = useState(false);

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    
    const result = await apiService.getApiInfo();
    
    if (result.success) {
      setData(result.data);
      setTimestamp(Date.now());
      setIsHealthy(true);
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

  const navigateTo = (screen) => {
    navigation.navigate(screen);
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>🚀 Node.js Test App</Text>
        <Text style={styles.subtitle}>Real-time API Monitoring & Testing</Text>
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
          <CardHeader title="Application Info" icon="🏠" onRefresh={fetchData} />
          <DataContainer
            data={data}
            loading={loading}
            error={error}
            timestamp={timestamp}
          />
        </Card>

        <Card>
          <CardHeader title="Available Endpoints" icon="📋" />
          <View style={styles.endpointList}>
            <TouchableOpacity
              style={styles.endpointItem}
              onPress={() => navigateTo('Health')}>
              <View style={styles.methodBadge}>
                <Text style={styles.methodText}>GET</Text>
              </View>
              <View style={styles.endpointInfo}>
                <Text style={styles.endpointPath}>/health</Text>
                <Text style={styles.endpointDesc}>Health check endpoint</Text>
              </View>
              <Text style={styles.arrow}>›</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.endpointItem}
              onPress={() => navigateTo('SystemInfo')}>
              <View style={styles.methodBadge}>
                <Text style={styles.methodText}>GET</Text>
              </View>
              <View style={styles.endpointInfo}>
                <Text style={styles.endpointPath}>/info</Text>
                <Text style={styles.endpointDesc}>System information</Text>
              </View>
              <Text style={styles.arrow}>›</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.endpointItem}
              onPress={() => navigateTo('Test')}>
              <View style={styles.methodBadge}>
                <Text style={styles.methodText}>GET</Text>
              </View>
              <View style={styles.endpointInfo}>
                <Text style={styles.endpointPath}>/test/:message</Text>
                <Text style={styles.endpointDesc}>Test with parameter</Text>
              </View>
              <Text style={styles.arrow}>›</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={[styles.endpointItem, styles.postEndpoint]}
              onPress={() => navigateTo('Echo')}>
              <View style={[styles.methodBadge, styles.postBadge]}>
                <Text style={[styles.methodText, styles.postText]}>POST</Text>
              </View>
              <View style={styles.endpointInfo}>
                <Text style={styles.endpointPath}>/echo</Text>
                <Text style={styles.endpointDesc}>Echo JSON data</Text>
              </View>
              <Text style={styles.arrow}>›</Text>
            </TouchableOpacity>
          </View>
        </Card>

        <View style={styles.footer}>
          <Text style={styles.footerText}>🎯 VDI Apps Container</Text>
          <Text style={styles.footerSubtext}>
            Server: {apiService.getBaseURL()}
          </Text>
        </View>
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
    paddingTop: 30,
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
    fontSize: 28,
    fontWeight: 'bold',
    color: Colors.primary,
    textAlign: 'center',
  },
  subtitle: {
    fontSize: 16,
    color: Colors.textSecondary,
    textAlign: 'center',
    marginTop: 5,
  },
  content: {
    flex: 1,
    padding: 15,
  },
  endpointList: {
    gap: 10,
  },
  endpointItem: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.background,
    padding: 15,
    borderRadius: 8,
    borderLeftWidth: 4,
    borderLeftColor: Colors.primary,
    marginBottom: 10,
  },
  postEndpoint: {
    borderLeftColor: Colors.warning,
  },
  methodBadge: {
    backgroundColor: Colors.successLight,
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 4,
    marginRight: 12,
  },
  postBadge: {
    backgroundColor: Colors.warning,
  },
  methodText: {
    fontSize: 12,
    fontWeight: '600',
    color: '#22543d',
  },
  postText: {
    color: Colors.warningDark,
  },
  endpointInfo: {
    flex: 1,
  },
  endpointPath: {
    fontSize: 16,
    fontWeight: '600',
    color: Colors.text,
    marginBottom: 2,
  },
  endpointDesc: {
    fontSize: 13,
    color: Colors.textSecondary,
  },
  arrow: {
    fontSize: 24,
    color: Colors.textSecondary,
  },
  footer: {
    padding: 20,
    alignItems: 'center',
    marginTop: 20,
  },
  footerText: {
    fontSize: 16,
    fontWeight: '600',
    color: Colors.text,
  },
  footerSubtext: {
    fontSize: 14,
    color: Colors.textSecondary,
    marginTop: 5,
  },
});

