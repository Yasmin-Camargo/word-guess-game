import { WordData } from '../interface/WordData';
import { useRemoveWord } from '../hooks/useRemoveWord'; 

import {
  Card as ChakraCard,
  CardHeader,
  CardBody,
  CardFooter,
  Button,
  Heading,
  Text,
  ButtonGroup,
} from '@chakra-ui/react';

interface CardProps {
  wordData: WordData;  
  refetch: () => void; 
}

/**
 * Deletes a word by calling the removeWord function and refetches the data.
 * @param {WordData} wordData - The word data to be deleted.
 * @param {function} removeWord - The function to remove the word from the backend.
 * @param {function} refetch - The function to refetch the word data after deletion.
 */
const deleteWord = async (wordData: WordData, removeWord: any, refetch: () => void) => {
  try {
    await removeWord(wordData.idWord);
    refetch();
  } catch (error) {
    console.error('Erro ao excluir palavra:', error);
  }
};

/**
 * Card component that displays word data and provides options to edit or delete the word.
 * @param {CardProps} props - The props for the component.
 * @returns {JSX.Element} The rendered Card component.
 */
export const Card = ({ wordData, refetch }: CardProps) => {  
  const { removeWord } = useRemoveWord();

  /**
   * Capitalizes the first letter of a string and converts the rest to lowercase.
   * @param {string} text - The text to be capitalized.
   * @returns {string} The capitalized string.
   */
  const capitalize = (text: string) => text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();

  return (
    <ChakraCard bg="whiteAlpha.800" borderWidth="1px" borderRadius="lg" boxShadow="xl">
      <CardHeader>
        <Heading size="md">{capitalize(wordData.word)}</Heading>
      </CardHeader>

      <CardBody>
        <Text>{capitalize(wordData.description)}</Text>
        <Text mt={2}>
          <b>Dica:</b> {wordData.synonymous.toLowerCase()}
        </Text>
        <Text mt={2} fontSize="sm" color="gray.500">
          Nível {wordData.level.toLowerCase()}
        </Text>
      </CardBody>

      <CardFooter>
        <ButtonGroup variant="outline" isAttached width="100%">
          <Button
            colorScheme="red"
            size="md"
            width="100%"
            onClick={() => deleteWord(wordData, removeWord, refetch)} 
          >
            Excluir 
          </Button>
        </ButtonGroup>
      </CardFooter>
    </ChakraCard>
  );
};
