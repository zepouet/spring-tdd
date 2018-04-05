package fr.treeptik.training.player;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceBDDMockitoTest {

    /*
    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @Before
    public void setUp() throws Exception {
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void rechercheJoueurParEmail() {
        given(playerRepository.findById("n.muller@treeptik.fr"))
                .willReturn(Optional.of(
                        new Player("n.muller@treeptik.fr", "Nicolas Muller", TypePlayer.CASUAL)));

        Player player = playerService.getDetails("n.muller@treeptik.fr");
        assertThat(player.getName()).isEqualTo("Nicolas Muller");
        assertThat(player.getType()).isEqualTo(TypePlayer.CASUAL);
    }


    @Test(expected = PlayerNotFoundException.class)
    public void rechercherJoueurInexistant() throws Exception {
        given(playerRepository.findById("nausicaa.muller@treeptik.fr")).willReturn(Optional.empty());
        playerService.getDetails("nausicaa.muller@treeptik.fr");
    }
    */
}