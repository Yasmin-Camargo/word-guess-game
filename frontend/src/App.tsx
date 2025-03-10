import { Box, Tabs, TabList, TabPanels, Tab, TabPanel} from '@chakra-ui/react';
import { GamePage } from './pages/gamePage';
import { WordList } from './pages/wordListPage'; 

function App() {

  return (
    <Box maxW="1280px" mx="auto" p={8} textAlign="center">
      <Tabs isFitted variant="enclosed">
        <TabList mb="1em">
          <Tab
            color="white"
            borderRadius="md"
            _selected={{ bg: "rgba(128, 128, 128, 0.4)", fontWeight: "bold" }}
            px={4}
          >
            Jogar
          </Tab>
          <Tab
            color="white"
            borderRadius="md"
            _selected={{ bg: "rgba(128, 128, 128, 0.2)", fontWeight: "bold" }}
            px={4}
          >
            Palavras Sorteadas
          </Tab>
        </TabList>

        <TabPanels>
          <TabPanel>
            <GamePage />
          </TabPanel>
          <TabPanel>
            <WordList />
          </TabPanel>
        </TabPanels>
      </Tabs>
    </Box>
  );
}

export default App;
