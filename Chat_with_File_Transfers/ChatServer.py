import sys
import socket
import select

socket_list = []
usernames = {}
socket_names = {}

def chat_server():
  if len(sys.argv) < 2:
    port = 6001
  else:
    port = sys.argv[1]

  server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
  server_socket.bind(('', int(port)))
  server_socket.listen(10)
 
  socket_list.append(server_socket)

  while True:
    ready_sockets, _,_ = select.select(socket_list,[],[],0)
    
    for sock in ready_sockets:
      if sock == server_socket: 
        sockfd, addr = server_socket.accept()
        socket_list.append(sockfd)
        usernames[sockfd.getpeername()] = "EMPTY"
      else:
        try:
          data = sock.recv(4096)
          if data:
            if usernames[sock.getpeername()] == "EMPTY":
              usernames[sock.getpeername()] = data.decode().rstrip()
              socket_names[usernames[sock.getpeername()]] = sock
            else:
              unencoded_data = data.decode()
              if unencoded_data[:1] == "*":
                print("I have a file request! " + unencoded_data)
                split_unencoded_data = unencoded_data.split(":")
                file_owner = split_unencoded_data[0][1:]
                file_owner_socket = socket_names[file_owner]
                file_name = split_unencoded_data[1]
                port = split_unencoded_data[2]
                sender_message = "*" + str(port) + ":" + file_name
                file_owner_socket.send(sender_message.encode())
                print("Done with file request" + unencoded_data)
              else:
                print("Message incoming")
                send_message(server_socket, sock, "\r" + usernames[sock.getpeername()] + ': ' + unencoded_data)  
                print("Message finished")
          else:
            if sock in socket_list:
              socket_list.remove(sock)

        except Exception as e:
          print("Exception : " + str(e))
          continue

  server_socket.close()
  
def send_message(server_socket, sock, message):
  for socket in socket_list:
    if socket != server_socket and socket != sock :
      try :
        socket.send(message.encode())
      except Exception as e:
        socket.close()
        if socket in socket_list:
          socket_list.remove(socket)
 
sys.exit(chat_server())