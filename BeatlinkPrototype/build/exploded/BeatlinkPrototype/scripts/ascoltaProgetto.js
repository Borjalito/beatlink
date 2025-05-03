

document.addEventListener('DOMContentLoaded', () => {
    const audioFilesElement = document.getElementById('audioFiles');
    const audioFiles = JSON.parse(audioFilesElement.getAttribute('data-files'));
    var maxDuration;
    if (audioFiles.length > 0) {
        const players = audioFiles.map((file,index) => {
           
                  const player = new Tone.Player({
                        url: file,
                        autostart: false,
                        onload: () => {
                            if (index === audioFiles.length - 1) {
                                // Calculate max duration when the last track is loaded
                                 maxDuration = Math.max(...players.map(p => p.buffer.duration));
                                
                            }
                        }
                    }).toDestination();
           
            player.sync().start(0);
              
              const wavesurfer = WaveSurfer.create({
                container: `#waveform${index}`,
                waveColor: '#012B28',
                progressColor: '#EBB42C'
            });
            
              wavesurfer.load(file);

              
              
              document.getElementById(`muteButton${index}`).addEventListener('click', () => {
                player.mute = !player.mute;
                document.getElementById(`muteButton${index}`).innerText = player.mute ? "Unmute" : "Mute";
            });
            
               document.getElementById(`soloButton${index}`).addEventListener('click', () => {
                const otherTracksMuted = players.every((p, i) => i === index || p.mute);
                if (otherTracksMuted) {
                    // If this track is the only non-muted track, unmute all tracks
                    players.forEach((p, i) => {
                        p.mute = false;
                        document.getElementById(`muteButton${i}`).innerText = "Mute";
                 });
                 } else {
                    // Mute all other tracks except the current one
                    players.forEach((p, i) => {
                        p.mute = i !== index;
                        document.getElementById(`muteButton${i}`).innerText = p.mute ? "Unmute" : "Mute";
                    });
                }
            });
            
			    document.getElementById(`volumeSlider${index}`).addEventListener('input', (event) => {
			        const volume = event.target.value;
			        player.volume.value = Tone.gainToDb(volume / 100);
			    });
            
            return player;
        });

			 const progressBar = document.getElementById('progressBar');

			
   	 function updateProgressBar() {
            const elapsedTime = Tone.Transport.seconds / maxDuration;
            progressBar.value = elapsedTime * 100;
            console.log("seconds update: "+ Tone.Transport.seconds);
            console.log("state update : "+ Tone.Transport.state);
            if(Tone.Transport.state === 'started' && Tone.Transport.seconds < maxDuration){
            requestAnimationFrame(updateProgressBar);
            }
        
    }
			
			
			   progressBar.addEventListener('input', (event) => {
		        console.log("Interferenza");
		        const progress = event.target.value / 100;
		        const newTime = progress * maxDuration;
		        Tone.Transport.seconds = newTime;
		        if(Tone.Transport.state !== 'started'){
				   Tone.Transport.pause();

				}
		    });
			
            const playAllTracks = async () => {

            if ( Tone.Transport.seconds >= maxDuration ) {
			console.log("seconds > maxDuration -> progresbarr=0")
         	Tone.Transport.position=0;
         	Tone.Transport.seconds=0;
			console.log("Transport seconds : "+Tone.Transport.seconds)

         	progressBar.value=0;

         	}
            Tone.Transport.start();
            console.log("stato : "+Tone.Transport.state)

            requestAnimationFrame(updateProgressBar);


            
        };

        const pauseAllTracks = () => {

           Tone.Transport.pause();
           console.log("stato : "+Tone.Transport.state)

        };

        const stopAllTracks = () => {

            Tone.Transport.stop();
            console.log("stato : "+Tone.Transport.state)
            progressBar.value=0;

        };

        // Aggiunge un evento per resettare la posizione quando tutte le tracce sono terminate
   

        document.getElementById('playButton').addEventListener('click', playAllTracks);
        document.getElementById('pauseButton').addEventListener('click', pauseAllTracks);
        document.getElementById('stopButton').addEventListener('click', stopAllTracks);
    }
});
