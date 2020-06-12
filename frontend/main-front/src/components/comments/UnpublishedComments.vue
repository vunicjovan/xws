<template>
    <div class="somediv">
        <div class="md-headline">Comments to be published</div>
        <md-list class="md-triple-line md-dense" v-for="comm in comments" v-bind:key="comm.id">
            <md-list-item>
                <md-avatar>
                    <i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
                </md-avatar>

                <div class="md-list-item-text">
                    <span class="ownerc">{{ comm.owner }}</span>
                    <span class="contentc">{{ comm.title }}</span>
                    <p>{{ comm.content }}</p>
                </div>
                
                <span class="some-span">
                    <md-button class="md-icon-button md-list-action">
                        <i class="fas fa-check-circle fa-2x"></i>
                        <md-tooltip>Publish comment</md-tooltip>
                    </md-button>
                    <md-button class="md-icon-button md-list-action">
                        <i class="fas fa-times-circle fa-2x"></i>
                        <md-tooltip>Reject comment</md-tooltip>
                    </md-button>
                </span>
            </md-list-item>
            <md-divider class="md-inset"></md-divider>
        </md-list>
    </div>
</template>

<script>
export default {
    name: "UnpublishedComments",
    data: function() {
        return {
            comments: [
                {
                    "id": 1,
                    "owner": "John Doe",
                    "title": "Hey! This is awesome!",
                    "content": "NICE ONE BRO",
                    "vehicle": "BMW X8"
                },
                {
                    "id": 2,
                    "owner": "Jack Smith",
                    "title": "NOPE",
                    "content": "This is not what I have expected...",
                    "vehicle": "Audi R8"
                },
                {
                    "id": 3,
                    "owner": "John Doe",
                    "title": "Hey! This is awesome!",
                    "content": "NICE ONE BRO",
                    "vehicle": "BMW X8"
                },
                {
                    "id": 4,
                    "owner": "Jimmy Page",
                    "title": "Oh yeah",
                    "content": "A car with a taste!",
                    "vehicle": "Reno 4"
                },
                {
                    "id": 5,
                    "owner": "Perica Ognjenovic",
                    "title": "Ima nas...",
                    "content": "... koji volimo Jugo.",
                    "vehicle": "Yugo Koral 45"
                },
                {
                    "id": 6,
                    "owner": "Erlich Bachmann",
                    "title": "B A C K M A N I T Y",
                    "content": "A real trouble with that gearbox, tho.",
                    "vehicle": "Dacia Logan"
                },
                {
                    "id": 7,
                    "owner": "Skitty Skittles",
                    "title": "Thunderstruck",
                    "content": "If you like my comment, you are pathetic loser.",
                    "vehicle": "BMW X8"
                },
                {
                    "id": 8,
                    "owner": "Jian Yang",
                    "title": "What if I told you",
                    "content": "There is an application for SEAFOOD. Not SEEfood, but SEAfood.",
                    "vehicle": "Tesla 2000"
                }
            ]
        };
    }
}
</script>

<style>
    p {
        word-break: break-all;
        white-space: normal;
        padding-right: 30%;
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    }
    .ownerc {
        font-family: 'Courier New', Courier, monospace;
    }
    .contentc {
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    }
    .some-span {
        padding-right: 2.5%;
    }
    .somediv {
        width: 70%;
        margin: 0 auto;
    }
    .md-headline {
        width: 50%;
        margin: 2.5% auto;
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
        text-align: center;
        vertical-align: middle;
    }
</style>
