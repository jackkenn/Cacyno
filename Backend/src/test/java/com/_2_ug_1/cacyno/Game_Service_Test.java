package com._2_ug_1.cacyno;

public class Game_Service_Test {
    private GameService _sut;
    private IUserRepo _userRepo;
    private IGameRepo _gameRepo;
    private List<User> _users;
    private List<Game> _games;
    
    @BeforeEach
    public void Game_Service_Test() {
        _userRepo = mock(IUserRepo.class);
        _gameRepo = mock(IGameRepo.class);
        _sut = new GameService();
        _users = new ArrayList<>();
        _games = new ArrayList<>();
        
        for(int i=0; i<3; i++) {
            Game game = new Game();
            game.setId(Integer.toString(i));
            _games.add(game);
            for(int j=0; j<4+i; j++) {
                User user = new User();
                user.setId(Integer.toString(_users.size()));
                user.setGame(game);
                user.setCurrent_game_money(1000);
                _users.add(user);
            }
        }

        when(_userRepo.getAllByGameId(any(String.class)).get()).return(x -> {
            new List<User> users = new ArrayList<>();
            for(User u : _users) {
                if(u.getGame().getId().equals(x.getArgument(0))) {
                    users.add(u);
                }
            }
            return users;
        });

        when(_gameRepo.AsyncGetById(any(String.class)).get()).return(x -> {
            for(Game g : _games) {
                if(g.getId().equals(x.getArgument(0))) {
                    return g;
                }
            }
        });

        doAnswer(x -> {
            for(User user : _users) {
                if(user.getId().equals(x.getArgument(0).getId())) {
                    _users.set(_users.indexOf(user), x.getArgument(0));
                }
                return null;
            }
        }).when(_userRepo).save(any(User.class));

        doAnswer(x -> {
            for(Game game : _games) {
                if(game.getId().equals(x.getArgument(0).getId())) {
                    _games.set(_games.indexOf(game), x.getArgument(0));
                }
                return null;
            }
        }).when(_gameRepo).save(any(Game.class));
    }

    @Test
    public void GameService_GameInit_BasicTest() {
        String testId = "0";
        _sut.gameInit(testId);
        for(User user : _users) {
            if(user.getId().equals(testId)) {
                
            }
        }
        
    }
    
}
