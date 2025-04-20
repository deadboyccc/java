// client.js
// 1. Import the net module
import net from 'net';

// 2. Create and connect the socket
const client = net.connect(
  { host: '127.0.0.1', port: 5000 },
  () => {
    // This callback is invoked once the TCP handshake completes
    console.log('Connected to server!');                      // :contentReference[oaicite:0]{index=0}
    
    // 3. Send an initial message (include line terminator if server expects it)
    client.write('Hello, server! This is the client.\r\n');    // :contentReference[oaicite:1]{index=1}
  }
);

// 4. Handle incoming data
client.on('data', (data) => {
  // Convert Buffer to string before printing
  console.log('Received data:', data);
});
// 5. Handle end-of-stream (server closed connection)
client.on('end', () => {
  console.log('Disconnected from server.');
});

// 6. Handle errors
client.on('error', (err) => {
  console.error('Socket error:', err);
});

// 7. Optional: listen for the same 'connect' event via an event listener
//    (identical to the connect-callback above; included here for illustration)
client.on('connect', () => {
  console.log('Successfully connected (via "connect" event).');
});
client.on('close', (hadError) => {
  console.log('Connection closed', hadError ? 'due to error' : 'gracefully');
});
client.on('timeout', () => {
  console.log('Socket timeout');
});
client.setTimeout(20000); // 20s idle timeout

// 8. Optional: Gracefully close from client side after some time
// setTimeout(() => {
//   console.log('Closing connection from client side.');
//   client.end();   // half-close: FIN sent, but can still receive data
// }, 5000);
