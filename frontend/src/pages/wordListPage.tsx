import { Box, Button, useDisclosure } from '@chakra-ui/react';
import { useWordData } from '../hooks/useWordData';
import { Card } from '../components/wordcard';
import WordForm from '../components/wordform';
import { WordData } from '../interface/WordData';
import { saveWord } from '../hooks/useWordForm'; 

/**
 * WordList component displays a list of words and allows users to add new words.
 * @returns {JSX.Element} The rendered WordList component.
 */
export const WordList = () => {
  const { data, refetch } = useWordData();
  const { isOpen, onOpen, onClose } = useDisclosure(); 

  /**
   * Handles saving a new word to the database.
   * @param {WordData} newWordData - The new word data to be saved.
   * @returns {Promise<void>} A promise that resolves when the word is successfully saved.
   * @throws {Error} Throws an error if saving the word fails.
   */
  const handleSave = async (newWordData: WordData) => { 
    try {
      await saveWord(newWordData); 
      refetch(); 
    } catch (error) {
      console.error('Erro ao adicionar palavra:', error); 
    }
  };

  return (
    <Box maxW="1280px" mx="auto" p={8} textAlign="center">
      <Button colorScheme="blue" size="md" onClick={onOpen}>
        + 
      </Button>
      <WordForm isOpen={isOpen} onClose={onClose} onSave={handleSave} /> 
      <Box
        display="grid"
        gridTemplateColumns="repeat(auto-fill, minmax(250px, 1fr))"
        gap={4}
        mt={8}
      >
        {data?.map((wordData) => (
          <Card key={wordData.idWord} wordData={wordData} refetch={refetch} />  
        ))}
      </Box>
    </Box>
  );
};
