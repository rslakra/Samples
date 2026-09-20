import React, {useState} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  SafeAreaView,
} from 'react-native';
import {Card, CardHeader, DataContainer, Input, Button} from '../components';
import {Colors} from '../constants/colors';
import apiService from '../services/api';

export const EchoScreen = () => {
  const [message, setMessage] = useState('');
  const [jsonData, setJsonData] = useState('');
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [timestamp, setTimestamp] = useState(null);

  const sendEcho = async () => {
    if (!message.trim()) {
      setError('Please enter a message');
      return;
    }

    setLoading(true);
    setError(null);

    let parsedData = null;
    if (jsonData.trim()) {
      try {
        parsedData = JSON.parse(jsonData);
      } catch (e) {
        setError('Invalid JSON in data field');
        setLoading(false);
        return;
      }
    }

    const result = await apiService.echo(message, parsedData);

    if (result.success) {
      setData(result.data);
      setTimestamp(Date.now());
    } else {
      setError(result.error);
    }

    setLoading(false);
  };

  const quickEcho = () => {
    const testData = {
      timestamp: new Date().toISOString(),
      random: Math.floor(Math.random() * 1000),
      source: 'React Native App',
    };

    setMessage('Hello from React Native!');
    setJsonData(JSON.stringify(testData, null, 2));
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>🔄 Echo Test</Text>
        <Text style={styles.subtitle}>POST JSON Data</Text>
      </View>

      <ScrollView style={styles.content}>
        <Card>
          <CardHeader title="Echo Request" icon="🔄" />

          <Input
            label="Message *"
            value={message}
            onChangeText={setMessage}
            placeholder="Enter your message"
          />

          <Input
            label="Data (JSON, optional)"
            value={jsonData}
            onChangeText={setJsonData}
            placeholder='{"key": "value", "number": 42}'
            multiline
          />

          <View style={styles.buttonContainer}>
            <Button
              title="Send Echo"
              onPress={sendEcho}
              loading={loading}
              style={styles.button}
            />
            <Button
              title="Fill Example"
              onPress={quickEcho}
              variant="secondary"
              style={styles.button}
            />
          </View>
        </Card>

        {(data || error) && (
          <Card>
            <CardHeader title="Response" icon="📄" />
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
              <Text style={styles.infoValue}>POST</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Path:</Text>
              <Text style={styles.infoValue}>/echo</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Content-Type:</Text>
              <Text style={styles.infoValue}>application/json</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Description:</Text>
              <Text style={styles.infoValue}>
                Echoes back the JSON data sent in the request body along with a
                timestamp
              </Text>
            </View>
          </View>
        </Card>

        <Card>
          <CardHeader title="Request Body Format" icon="💡" />
          <View style={styles.exampleContainer}>
            <Text style={styles.exampleLabel}>Request:</Text>
            <Text style={styles.exampleCode}>
              {`{
  "message": "Hello World",
  "data": {
    "key": "value",
    "number": 42
  }
}`}
            </Text>
          </View>
          <View style={[styles.exampleContainer, styles.responseExample]}>
            <Text style={styles.exampleLabel}>Response:</Text>
            <Text style={styles.exampleCode}>
              {`{
  "echo": {
    "message": "Hello World",
    "data": {
      "key": "value",
      "number": 42
    }
  },
  "timestamp": "2024-01-15T10:30:00.000Z",
  "receivedAt": "1/15/2024, 10:30:00 AM"
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
  buttonContainer: {
    flexDirection: 'row',
    gap: 10,
  },
  button: {
    flex: 1,
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
  responseExample: {
    marginTop: 15,
  },
  exampleLabel: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.text,
    marginBottom: 8,
  },
  exampleCode: {
    fontFamily: 'monospace',
    fontSize: 12,
    color: Colors.text,
  },
});

