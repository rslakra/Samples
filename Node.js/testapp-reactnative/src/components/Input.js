import React from 'react';
import {View, Text, TextInput, StyleSheet} from 'react-native';
import {Colors} from '../constants/colors';

export const Input = ({label, value, onChangeText, placeholder, multiline, style}) => {
  return (
    <View style={[styles.container, style]}>
      {label && <Text style={styles.label}>{label}</Text>}
      <TextInput
        value={value}
        onChangeText={onChangeText}
        placeholder={placeholder}
        placeholderTextColor={Colors.textSecondary}
        style={[styles.input, multiline && styles.multiline]}
        multiline={multiline}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginBottom: 15,
  },
  label: {
    fontSize: 14,
    fontWeight: '600',
    color: Colors.text,
    marginBottom: 8,
  },
  input: {
    backgroundColor: Colors.card,
    borderWidth: 1,
    borderColor: Colors.border,
    borderRadius: 8,
    paddingHorizontal: 15,
    paddingVertical: 12,
    fontSize: 16,
    color: Colors.text,
  },
  multiline: {
    minHeight: 100,
    textAlignVertical: 'top',
  },
});

