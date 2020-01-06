var sessionForm = new Vue({
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

Vue.component('player-result-row', {
    template: '#player-result-row',
    data: function() {
       return {
           showInput: false
       }
    },
    props: {
        sessionId: Number,
        playerResult: Object
    },
    methods: {
        save: function() {
            axios
                .post('/api/sessions/' + this.sessionId + '/player/' + this.playerResult.player.playerId, {
                    result: this.playerResult.result,
                    buyIns: this.playerResult.buyIns
                })
                .then(response => {
                    this.showInput = !this.showInput;
                });
        },
        incrementBuyIns: function() {
            this.changeBuyIns(this.playerResult.buyIns + 1);
        },
        decrementBuyIns: function() {
            this.changeBuyIns(this.playerResult.buyIns - 1);
        },
        changeBuyIns: function(buyIns) {
            axios
                .post('/api/sessions/' + this.sessionId + '/player/' + this.playerResult.player.playerId, {
                    result: this.playerResult.result,
                    buyIns: buyIns
                })
                .then(response => {
                    this.playerResult.buyIns = buyIns;
                });
        }
    }
});


Vue.component('players-results-panel', {
    template: '#players-results-panel',
    props: {
        sessionId: Number
    },
    data: function() {
        return {
            session: {}
        }
    },
    mounted() {
        axios
            .get('/api/sessions/' + this.sessionId)
            .then(response => {
                this.session = response.data;
            })
    },
    methods: {
        deletePlayerResult: function(playerId) {

            axios
                .delete('/api/sessions/' + this.sessionId + '/player/' + playerId)
                .then(response => {
                    this.session = response.data;
                })
        }
    }
});


var sessionDetail = new Vue({
    el: '#session-detail'
});