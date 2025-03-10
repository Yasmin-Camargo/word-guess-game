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

interface GameConfigFormProps {
  isOpen: boolean;
  onClose: () => void;
  onSave: (newGameConfig: GameConfig) => void;
}

const GameConfigForm: React.FC<GameConfigFormProps> = ({ isOpen, onClose, onSave }) => {
  const [theme, setTheme] = useState('tecnologia');
  const [difficulty, setDifficulty] = useState('1');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (name === 'theme') setTheme(value);
    if (name === 'difficulty') setDifficulty(value);
    console.log('difficulty: ', difficulty);
  };

  const handleSubmit = async () => {
    const newGameConfig: GameConfig = { difficulty, theme };
    onSave(newGameConfig);
    onClose(); 
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
              <option value="Fácil">Fácil</option> 
              <option value="Médio">Médio</option>
              <option value="Difícil">Difícil</option>
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

export default GameConfigForm; 
