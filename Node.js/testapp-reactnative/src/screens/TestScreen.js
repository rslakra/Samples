import React, {useState} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  SafeAreaView,
  TouchableOpacity,
} from 'react-native';
import {Card, CardHeader, DataContainer, Input, Button} from '../components';
import {Colors} from '../constants/colors';
import apiService from '../services/api';

export const TestScreen = () => {
  const [message, setMessage] = useState('');
  const [format, setFormat] = useState('');
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [timestamp, setTimestamp] = useState(null);

  const fetchData = async () => {
    if (!message.trim()) {
      setError('Please enter a message');
      return;
    }

    setLoading(true);
    setError(null);

    const result = await apiService.testMessage(
      message,
      format.trim() || null
    );

    if (result.success) {
      setData(result.data);
      setTimestamp(Date.now());
    } else {
      setError(result.error);
    }

    setLoading(false);
  };

  const quickTest = async (testMessage) => {
    setMessage(testMessage);
    setFormat('');
    
    setLoading(true);
    setError(null);

    const result = await apiService.testMessage(testMessage);

    if (result.success) {
      setData(result.data);
      setTimestamp(Date.now());
    } else {
      setError(result.error);
    }

    setLoading(false);
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>🧪 Test Endpoint</Text>
        <Text style={styles.subtitle}>Test with Path Parameters</Text>
      </View>

      <ScrollView style={styles.content}>
        <Card>
          <CardHeader title="Test Parameters" icon="🧪" />
          
          <Input
            label="Message"
            value={message}
            onChangeText={setMessage}
            placeholder="Enter a message (e.g., hello)"
          />

          <Input
            label="Format (optional)"
            value={format}
            onChangeText={setFormat}
            placeholder="simple or leave empty for json"
          />

          <Button
            title="Test Endpoint"
            onPress={fetchData}
            loading={loading}
          />

          <View style={styles.quickTestContainer}>
            <Text style={styles.quickTestLabel}>Quick Test:</Text>
            <View style={styles.quickTestButtons}>
              <TouchableOpacity
                style={styles.quickTestButton}
                onPress={() => quickTest('hello')}>
                <Text style={styles.quickTestButtonText}>hello</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.quickTestButton}
                onPress={() => quickTest('world')}>
                <Text style={styles.quickTestButtonText}>world</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.quickTestButton}
                onPress={() => quickTest('awesome')}>
                <Text style={styles.quickTestButtonText}>awesome</Text>
              </TouchableOpacity>
            </View>
          </View>
        </Card>

        {(data || error) && (
          <Card>
            <CardHeader title="Response" icon="📄" onRefresh={fetchData} />
            <DataContainer
              data={data}
              loading={loading}
              error={error}
              timestamp={timestamp}
            />
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
              <Text style={styles.infoValue}>/test/:message</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Query Params:</Text>
              <Text style={styles.infoValue}>
                format (optional) - "simple" for plain text
              </Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Description:</Text>
              <Text style={styles.infoValue}>
                Returns the message in various formats: uppercased, lowercased,
                reversed, and length
              </Text>
            </View>
          </View>
        </Card>

        <Card>
          <CardHeader title="Example Response" icon="💡" />
          <View style={styles.exampleContainer}>
            <Text style={styles.exampleCode}>
              {`{
  "message": "hello",
  "uppercased": "HELLO",
  "lowercased": "hello",
  "length": 5,
  "reversed": "olleh",
  "timestamp": "2024-01-15T10:30:00.000Z"
}`}
            </Text>
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
  quickTestContainer: {
    marginTop: 20,
  },
  quickTestLabel: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.text,
    marginBottom: 10,
  },
  quickTestButtons: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 10,
  },
  quickTestButton: {
    backgroundColor: Colors.background,
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 6,
    borderWidth: 1,
    borderColor: Colors.primary,
  },
  quickTestButtonText: {
    color: Colors.primary,
    fontSize: 14,
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
    width: 110,
  },
  infoValue: {
    flex: 1,
    fontSize: 14,
    color: Colors.textSecondary,
  },
  exampleContainer: {
    backgroundColor: Colors.codeBackground,
    borderRadius: 6,
    padding: 15,
  },
  exampleCode: {
    fontFamily: 'monospace',
    fontSize: 12,
    color: Colors.text,
  },
});

