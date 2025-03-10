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


const deleteWord = async (wordData: WordData, removeWord: any, refetch: () => void) => {
  try {
    await removeWord(wordData.idWord);
    refetch();
  } catch (error) {
    console.error('Erro ao excluir palavra:', error);
  }
};

export const Card = ({ wordData, refetch }: CardProps) => {  
  const { removeWord } = useRemoveWord();

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
