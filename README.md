# Trading system simulation
a concurrent price updater and multiple trader tradingat the same time. 
This project combines foundamental concurrency concept (and server/cliet socket). 
The proejct consists of 
(1.)sephamore which only allows a set number of ppl 
to get into the investment counter to buy stocks,
(2.)reentrant locks to update the stocks' prices,
(3.)fixed thread pool size for a max of clients.
(4.)non-blocking IO to terminate the program.
