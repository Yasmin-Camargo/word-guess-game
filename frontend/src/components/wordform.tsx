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
import { WordData } from '../interface/WordData'; 

interface WordFormProps {
  isOpen: boolean; 
  onClose: () => void; 
  onSave: (newWordData: WordData) => Promise<void>; 
}

const WordForm: React.FC<WordFormProps> = ({ isOpen, onClose, onSave }) => {
  const [word, setWord] = useState('');
  const [description, setDescription] = useState('');
  const [synonymous, setSynonymous] = useState('');
  const [level, setLevel] = useState('');

  /**
   * Handles input changes by updating the respective state variables.
   * @param {React.ChangeEvent<HTMLInputElement | HTMLSelectElement>} e - The change event.
   */
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (name === 'word') setWord(value);
    if (name === 'description') setDescription(value);
    if (name === 'synonymous') setSynonymous(value);
    if (name === 'level') setLevel(value);
  };

  /**
   * Resets the form fields to their initial empty state.
   */
  const resetForm = () => {
    setWord('');
    setDescription('');
    setSynonymous('');
    setLevel('');
  };

  /**
   * Handles the form submission by creating a new WordData object,
   * saving it via the onSave function, and then closing the modal and resetting the form.
   */
  const handleSubmit = async () => {
    const newWordData: WordData = { word, description, synonymous, level };
    await onSave(newWordData); 
    onClose(); 
    resetForm();
  };

  return (
    <Modal initialFocusRef={React.useRef(null)} isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Adicionar nova palavra</ModalHeader> 
        <ModalCloseButton />
        <ModalBody pb={6}>
          <FormControl isRequired>
            <FormLabel>Palavra</FormLabel>
            <Input
              name="word"
              value={word}
              onChange={handleChange}
              placeholder='Palavra para ser adivinhada' 
            />
          </FormControl>
          <FormControl isRequired mt={4}>
            <FormLabel>Descrição</FormLabel>
            <Input
              name="description"
              value={description}
              onChange={handleChange}
              placeholder='Descrição da palavra' 
            />
          </FormControl>
          <FormControl isRequired mt={4}>
            <FormLabel>Dica</FormLabel>
            <Input
              name="synonymous"
              value={synonymous}
              onChange={handleChange}
              placeholder='Dica (uma palavra)' 
            />
          </FormControl>
          <FormControl isRequired mt={4}>
            <FormLabel>Nível</FormLabel>
            <Select
              name="level"
              value={level}
              onChange={handleChange}
              placeholder='Selecione o nível' 
            >
              <option value="1">1</option> 
              <option value="2">2</option>
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
