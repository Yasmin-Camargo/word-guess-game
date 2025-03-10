import React, { useState } from 'react';
import {
  FormControl,
  FormLabel,
  Input,
  Select,
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
} from '@chakra-ui/react';
import { GameConfig } from '../interface/GameConfig'; 

interface WordFormProps {
  isOpen: boolean; 
  onClose: () => void; 
  onSave: (newGameConfig: GameConfig) => Promise<void>; 
}

const WordForm: React.FC<WordFormProps> = ({ isOpen, onClose, onSave }) => {
  const [theme, setTheme] = useState('');
  const [difficulty, setDifficulty] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (name === 'theme') setTheme(value);
    if (name === 'difficulty') setDifficulty(value);
  };


  const resetForm = () => {
    setTheme('');
    setDifficulty('');
  };


  const handleSubmit = async () => {
    const newGameConfig: GameConfig = { theme, difficulty };
    await onSave(newGameConfig); 
    onClose(); 
    resetForm();
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Configuração do sorteio da palavra</ModalHeader> 
        <ModalCloseButton />
        <ModalBody pb={6}>
          <FormControl isRequired mt={4}>
            <FormLabel>Tema</FormLabel>
            <Input
              name="theme"
              value={theme}
              onChange={handleChange}
              placeholder='Tema' 
            />
          </FormControl>
          <FormControl isRequired mt={4}>
            <FormLabel>Dificuldade da palavra</FormLabel>
            <Select
              name="difficulty"
              value={difficulty}
              onChange={handleChange}
            >
              <option value="1">Fácil</option> 
              <option value="2">Médio</option>
              <option value="3">Difícil</option>
            </Select>
          </FormControl>
        </ModalBody>
        <ModalFooter>
          <Button colorScheme="blue" mr={3} onClick={handleSubmit}>
            Salvar
          </Button>
          <Button onClick={onClose}>Cancelar</Button> 
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

export default WordForm; 
