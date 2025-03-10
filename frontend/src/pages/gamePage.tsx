import { useState, useEffect } from 'react';
import { Box, Heading, Input, Button, Text, Stack, Card, CardBody, CardHeader, Flex, HStack, useDisclosure } from '@chakra-ui/react';
import { ViewIcon } from '@chakra-ui/icons';
import { startGame, useCheckWord } from '../hooks/useGame';
import GameConfigForm from '../components/gameConfigForm';
import { useWordData } from '../hooks/useWordData';
import { saveWord } from '../hooks/useGameForm';
import { GameConfig } from '../interface/GameConfig';

export const GamePage = () => {
  const [word, setWord] = useState(''); 
  const [description, setDescription] = useState(''); 
  const [synonymous, setSynonymous] = useState('?'); 
  const [showHint, setShowHint] = useState(false);
  const [showDescription, setShowDescription] = useState(false); 
  const [isGameStarted, setIsGameStarted] = useState(false);
  const [loading, setLoading] = useState(false);
  const { message, setMessage, finish, setFinish, checkWord } = useCheckWord(); 

  const handleStartGame = () => {
    setLoading(true);
    startGame()
      .then(body => {
        setDescription(body.description);
        setSynonymous(body.synonymous);
        setMessage('');
        setFinish(false);
        setShowHint(false);
        setShowDescription(true);
        setIsGameStarted(true);
      })
      .catch(error => {
        console.error('Erro ao iniciar o jogo:', error);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleCheckWord = () => {
    checkWord(word);
    setWord('');
  };

  useEffect(() => {
    // Resets the game state if the game is finished
    if (finish) {
      setIsGameStarted(false);
      setShowDescription(false);
      setDescription('');
    }
  }, [finish]);

  const handleShowHint = () => {
    setShowHint(true);
    checkWord('showsynonymous');
  };

  const { data, refetch } = useWordData();
  const { isOpen, onOpen, onClose } = useDisclosure();

  const handleSave = async (newGameConfig: GameConfig) => {
    console.log("Salvando nova configuração do jogo:", newGameConfig); // Adicione este log
  
    try {
      await saveWord(newGameConfig);
      refetch();
    } catch (error) {
      console.error("Erro ao adicionar palavra:", error);
    }
  };
  
  return (
    <Box maxW="1280px" mx="auto" p={8} textAlign="center">
      <Heading 
        as="h1" 
        size="2xl" 
        color="white" 
        mb={8}
        textShadow="2px 2px 4px rgba(0, 0, 0, 0.5)"
      >
        Jogo de Adivinhação de Palavras
      </Heading>

      <Text as='b' size='lg' color="pink" mt={4} p={5}>
        {message}
      </Text>

      <Flex justify="center" p={5}>
        <Card 
          align="center" 
          bg="whiteAlpha.800"  
          boxShadow="dark-lg" 
          borderRadius="lg" 
          transform="translateY(-5px)" 
          transition="transform 0.3s ease-in-out"
          maxW="500px" 
          width="100%" 
        >
          <CardHeader display="flex" flexDirection="column" alignItems="center">
            <Heading style={{ fontSize: '1.5rem' }} color="black.700">
              {description}
            </Heading>
          </CardHeader>
          <CardBody>
            {showDescription ? (
              <HStack spacing={2}>
                <span style={{ fontSize: '3rem' }}>🤔​ </span>
                <Text fontWeight="bold">Dica:</Text>
                {!showHint ? (
                  <ViewIcon w={8} h={8} cursor="pointer" onClick={handleShowHint} color="purple" />
                ) : (
                  <Text>{synonymous}</Text>
                )}
              </HStack>
            ) : (
              <Box>
                <Text as='b' style={{ fontSize: '1.2rem' }}>
                  Você tem 3 tentativas para adivinhar a palavra.
                </Text>
                <span style={{ fontSize: '3rem' }}>🎲</span>

                <br />
                <Text as='kbd'>
                  Lembre-se: usar uma dica contará como uma tentativa.
                </Text>
              </Box>
            )}
            
          </CardBody>
        </Card>
      </Flex>

      <Stack spacing={4} maxW="500px" mx="auto">
      {isGameStarted && (
  <Input
    placeholder="Digite sua tentativa"
    value={word}
    onChange={(e) => setWord(e.target.value)}
    color="black"
    size="lg"
    bg="white"
  />
)}

      </Stack>
    
      <Button 
        colorScheme={isGameStarted ? "purple" : "blue"} 
        size="md" 
        onClick={isGameStarted ? handleCheckWord : handleStartGame} 
        mt={4}
        isDisabled={loading}
      >
        {isGameStarted ? "Verificar" : "Sortear nova palavra"} 
      </Button>

      <Flex justify="flex-end" mb={4}>
        <Button colorScheme="white" size="md" onClick={onOpen} leftIcon={<span style={{ fontSize: '1.5rem' }}>⚙️</span>}>
          Configurações
        </Button>
      </Flex>
      <GameConfigForm isOpen={isOpen} onClose={onClose} onSave={handleSave} />
      <br />
    </Box>
  );
};