var app = new Vue({
    el: '#session-form-app',
    data: {
        players: [],
        selectedPlayers: [],
        playerSearch: ''
    },
    mounted() {
        axios
            .get('/api/players')
            .then(response => {
                this.players = response.data;
            })
    },
    computed: {
        filteredPlayers() {
            return this.players.filter(player =>  {
                return (player.firstName.toLowerCase().indexOf(this.playerSearch.toLowerCase()) >= 0)
                    || (player.lastName.toLowerCase().indexOf(this.playerSearch.toLowerCase()) >= 0)
            }).sort((a, b) => {
                if(a.playerId < b.playerId) { return -1; }
                if(a.playerId > b.playerId) { return 1; }
                return 0;
            });
        }
    },
    methods: {
        selectPlayer(player) {
            this.selectedPlayers.push(player);
            this.players = this.players.filter(p => p.playerId !== player.playerId);
        },
        unselectPlayer(player) {
            this.players.push(player);
            this.selectedPlayers = this.selectedPlayers.filter(p => p.playerId !== player.playerId);
        }
    }
});
