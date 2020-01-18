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
            });
        }
    },
    methods: {
        selectPlayer(player) {
            this.selectedPlayers.push(player);
            this.players = this.players.filter(p => p.playerId !== player.playerId);
            this.playerSearch = '';
            this.$refs.searchInput.focus();
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
        },
        toggleInput: function() {
            this.showInput = !this.showInput;
            var self = this;
            this.$nextTick(() => {
                this.$refs.resultInput.focus();
                this.$refs.resultInput.select();
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
            session: {},
            otherPlayers: [],
            playerSearch: ''
        }
    },
    mounted() {
        axios
            .get('/api/sessions/' + this.sessionId)
            .then(response => {
                this.session = response.data;
                axios
                    .get('/api/players')
                    .then(response => {
                        this.otherPlayers = response.data;
                        this.otherPlayers = this.otherPlayers.filter(player =>
                            this.session.playerResults.filter(playerResult => playerResult.player.playerId == player.playerId).length === 0
                        );
                    })
            })
    },
    methods: {
        deletePlayerResult: function(playerId) {
            let playerToBeDeleted = this.session.playerResults.filter(p => p.player.playerId === playerId)[0].player;
            axios
                .delete('/api/sessions/' + this.sessionId + '/player/' + playerId)
                .then(response => {
                    this.session = response.data;
                    this.otherPlayers = [...this.otherPlayers, playerToBeDeleted];
                })
        },
        addPlayer: function(playerId) {
          axios
              .post('/api/sessions/' + this.sessionId + '/player/' + playerId, {
                  result: 0,
                  buyIns: 0
              })
              .then(response => {
                  this.session = response.data;
                  this.hideModal();
                  this.otherPlayers = this.otherPlayers.filter(p => p.playerId !== playerId);
              });
        },
        showModal: function() {
            $('#add-player').modal('show');
        },
        hideModal: function() {
            $('#add-player').modal('hide');
        }
    },
    computed: {
        filteredPlayers() {
            return this.otherPlayers.filter(player =>  {
                return (player.firstName.toLowerCase().indexOf(this.playerSearch.toLowerCase()) >= 0)
                    || (player.lastName.toLowerCase().indexOf(this.playerSearch.toLowerCase()) >= 0)
            });
        }
    }
});


var sessionDetail = new Vue({
    el: '#session-detail'
});


$(function(){
    axios.get('/api/stats')
        .then(response => {

            $('.pivot-sum').each(function() {
                let year = $(this).attr('data-year');
                $(this).pivotUI(response.data, {
                    rows: ['firstName'], cols: [], vals: ['result'], aggregatorName: 'Somme en entiers',
                    filter: function(e) { return year == 'all' || e.year == year }, showUI: false, rowOrder: 'value_z_to_a',
                    rendererName: "Carte de chaleur",
                    rendererOptions: {
                        heatmap: {
                            colorScaleGenerator: function(values) {
                                return Plotly.d3.scale.linear()
                                    .domain([Plotly.d3.min(values), 0, Plotly.d3.max(values)])
                                    .range(["#e67c73", "#ffd666", "#57bb8a"])
                            }
                        }
                    }
                }, false, 'fr');
            });

            $('.pivot-detail').each(function() {
                let year = $(this).attr('data-year');
                $(this).pivotUI(response.data, {
                    rows: ['firstName'], cols: ['date'], vals: ['result'], aggregatorName: 'Somme en entiers',
                    filter: function(e) { return year == 'all' || e.year == year }, showUI: false, rowOrder: 'value_z_to_a',
                    rendererName: "Carte de chaleur",
                    rendererOptions: {
                        heatmap: {
                            colorScaleGenerator: function(values) {
                                return Plotly.d3.scale.linear()
                                    .domain([Plotly.d3.min(values), 0, Plotly.d3.max(values)])
                                    .range(["#e67c73", "#ffd666", "#57bb8a"])
                            }
                        }
                    }
                }, false, 'fr');
            });

            $('.pivot-number').each(function() {
                let year = $(this).attr('data-year');
                $(this).pivotUI(response.data, {
                    rows: ['firstName'], cols: [], aggregatorName: 'Nombre',
                    filter: function(e) { return year == 'all' || e.year == year }, showUI: false, rowOrder: 'value_z_to_a'
                }, false, 'fr');
            });

            $('.pivot-deviation').each(function() {
                let year = $(this).attr('data-year');
                $(this).pivotUI(response.data, {
                    rows: ['firstName'], cols: [], vals: ['result'], aggregatorName: 'Ecart-type',
                    filter: function(e) { return year == 'all' || e.year == year }, showUI: false, rowOrder: 'value_z_to_a'
                }, false, 'fr');
            })
            var plotRenderers = $.extend($.pivotUtilities.renderers,
                $.pivotUtilities.plotly_renderers);
             $(".pivot-stats").pivotUI(response.data, {
                 rows: ['firstName'], cols: ['date'], vals: ['result'], aggregatorName: 'Somme en entiers',
                 renderers: plotRenderers,
                 showUI: true
             }, false, 'fr');
        });
});

$(function(){
    let renderOptions = {
        plotly: {
            autosize: true,
            showlegend: false,
            width: document.getElementsByClassName('pivot')[0].clientWidth,
            height: 400,
            xaxis: {
                tickangle: 90,
                fixedrange: true,
            },
            yaxis: {
                fixedrange: true,
            }
        },
        plotlyConfig: {
            responsive: true,
        }
    };

    $('.session-pivot').each(function() {
        let sessionId = $(this).attr('data-session-id')
        var plotRenderers = $.extend($.pivotUtilities.renderers,
            $.pivotUtilities.plotly_renderers);
        axios.get('/api/stats/' + sessionId)
            .then(response => {
                $('.session-pivot').each(function() {
                    let year = $(this).attr('data-year');
                    renderOptions.plotly.title = 'Classement de la session';
                    $(this).pivotUI(response.data, {
                        rows: ['firstName'], cols: ['firstName'], vals: ['result'], aggregatorName: 'Somme en entiers',
                        rendererName: 'Bar Chart',
                        showUI: false, rowOrder: 'value_z_to_a',
                        renderers: plotRenderers,
                        rendererOptions: renderOptions,
                    }, false, 'fr');
                });
            });
    });
    $('.until-session-pivot').each(function() {
        let sessionId = $(this).attr('data-session-id')
        var plotRenderers = $.extend($.pivotUtilities.renderers,
            $.pivotUtilities.plotly_renderers);
        axios.get('/api/stats/until/' + sessionId)
            .then(response => {
                $('.until-session-pivot').each(function() {
                    let year = $(this).attr('data-year');
                    renderOptions.plotly.title = 'Classement de la saison';
                    $(this).pivotUI(response.data, {
                        rows: ['firstName'], cols: ['firstName'], vals: ['result'], aggregatorName: 'Somme en entiers',
                        rendererName: 'Bar Chart',
                        showUI: false, rowOrder: 'value_z_to_a',
                        renderers: plotRenderers,
                        rendererOptions: renderOptions,
                    }, false, 'fr');
                });
            });
    });
});
//
//
//
// $(function(){
//     axios.get('/api/stats')
//         .then(response => {
//             $("#pivot-ui").pivotUI(response.data, {
//                 rows: ['firstName'], cols: ['date'], vals: ['result'], aggregatorName: 'Somme en entiers',
//                 filter: function(e) { return e.year === 2019 }, showUI: true
//             }, false, 'fr');
//         });
// });