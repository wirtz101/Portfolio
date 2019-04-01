import sys
import socket
import select
import getopt
 
def chat_client():

  def send_message():
    message = sys.stdin.readline()
    if not message:
      exit_program()
    client_server_socket.send(message.encode())
    print('')

  def receive_message():
    data = sock.recv(4096)
    if not data:
      exit_program()
    else:
      unencoded_data = data.decode()
      if unencoded_data[:1] == "*":
        split_unencoded_data = unencoded_data.split(":")
        port = split_unencoded_data[0][1:]
        file_name = split_unencoded_data[1]
        print("I should send my file to " + unencoded_data)
        send_file(port, file_name)
      else:
        print(unencoded_data)

  def request_file():
    print("Who owns the file?")
    file_owner = sys.stdin.readline()
    print("Which file do you want?")
    file_name = sys.stdin.readline().strip('\n')
    request = "*" + file_owner.strip('\n') + ":" + file_name.strip('\n') + ":" + str(client_port_number)
    client_server_socket.send(request.encode())
    receive_file(file_name)

  def send_file(port, file_name):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    client_socket.bind(('0.0.0.0', client_port_number))

    port = int(port)

    try:
      print("Connecting to " + str(port))
      client_socket.connect(('', port))
    except Exception as e:
      print("Exception connecting (send) :" + str(e))
      client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
      client_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
      client_socket.bind(('0.0.0.0', client_port_number))
      client_socket.connect(('', port))
    f = open(file_name,'rb')
    l = f.read(1024)
    while (l):
      client_socket.send(l)
      l = f.read(1024)
    f.close()
    client_socket.close()
  
  def receive_file(file_name):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    client_socket.bind(('0.0.0.0', client_port_number))
    client_socket.listen(10)

    try:
      print("I am " + str(client_port_number))
      sock, addr = client_socket.accept()
      print("Accepted connection...")
    except Exception as e:
      print("Exception connecting (receive) :" + str(e))
    f = open(file_name, 'wb')
    print("about to write to file to ", file_name)
    data = sock.recv(1024)
    print("data: ", data)
    while data:
      f.write(data)
      data = sock.recv(1024)
      print("data: ", data)
    f.close()
    client_socket.close()

  def exit_program():
    try:
      client_server_socket.close()
    except:
      pass
    try:
      client_socket.close()
    except:
      pass
    sys.exit()

  try: 
    options, args = getopt.getopt(sys.argv[1:], 'l:p:')
  except getopt.GetoptError as err:
    print( str(err) )
    exit_program()

  options_dictionary = dict(options)

  client_port_number = int(options_dictionary["-l"])
  server_port_number = int(options_dictionary["-p"])
   
  client_server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  
   
  try:
    client_server_socket.connect(('', server_port_number))

  except Exception as e:
    print("Error: " + str(e))
    exit_program()
   
  print('Enter your name:')
  message = sys.stdin.readline()
  client_server_socket.send(message.encode())
  
  while True:
    print("Enter an option ('m', 'f', 'x'):\
    (M)essage (send)\
    (F)ile (request)\
    e(X)it")
    socket_list = [sys.stdin, client_server_socket]
     
    ready_sockets,_,_ = select.select(socket_list , [], [])
     
    
    for sock in ready_sockets:       
      if sock == client_server_socket:
        receive_message()
      else:
        option_choice = sys.stdin.readline().strip('\n')
        if option_choice == "m":
          print("Enter your message:")
          send_message()
        elif option_choice == "f":
          request_file()
        elif option_choice == "x":
          print("closing your sockets... goodbye")
          exit_program()

sys.exit(chat_client())