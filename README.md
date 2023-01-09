# EscalonamentoCPU

O escalonamento de processos ou agendador de tarefas (em inglês scheduling) é uma atividade organizacional feita pelo escalonador (scheduler) da CPU ou de um sistema distribuído, possibilitando executar os processos mais viáveis e concorrentes, priorizando determinados tipos de processos, como os de I/O Bound e os CPU Bound.

O escalonador de processo é um processo que deve ser executado quando da mudança de contexto (troca de processo), ao passo que ele escolhe o processo que será executado pela CPU, sendo o escalonamento realizado com o auxílio do hardware.

Tipos de Escalonamento:

1 -> FIFO (First in, first out) ou FCFS (First come, first served), em português:"primeiro que entra, primeiro que sai".

2 -> SJF (Shortest Job First): Onde o menor processo ganhará a CPU e atrás do mesmo formar uma fila de processos por ordem crescente de tempo de execução, não-preemptivo. Desvantagem: baixo aproveitamento quando se tem poucos processos prontos para serem executados.

3 -> SRT (Shortest Remaining Time): Neste algoritmo é escolhido o processo que possua o menor tempo restante, mesmo que esse processo chegue à metade de uma operação, se o processo novo for menor ele será executado primeiro, preemptivo. Desvantagem: processos que consomem mais tempo de execução podem demorar muito para serem finalizados se muitos processos com curto tempo de execução chegarem.

4 -> SRTF(menor tempo restante primeiro) SRTF é um escalonamento preempitivo se um processo chega a fila de prontos com um tempo de Burst menor que o tempo restante
do processo em execução então há preempção.

5 -> RR (Round-Robin): Inspirado na história de Robin Hood onde, na procura de justiça, Robin roubava dos ricos para entregar aos pobres, fazendo assim com que todos no seu reino tivesse o mesmo tanto de bens. Uma das mais simples e robustas entre as atuais técnicas utilizadas para problemas de distribuição de carga, nesse escalonamento o sistema operacional possui um timer, chamado de quantum, onde todos os processos ganham o mesmo valor de quantum para rodarem na CPU, depois que o quantum acaba e o processo não terminou, ocorre uma preempção e o processo é inserido no fim da fila. Se o processo termina antes de um quantum, a CPU é liberada para a execução de novos processos. Em ambos os casos, após a liberação da CPU, um novo processo é escolhido na fila. Novos processos são inseridos no fim da fila.Quando um processo é retirado da fila para a CPU, ocorre uma troca de contexto, o que resulta em um tempo adicional na execução do processo.Esta técnica remove a necessidade de criar sistemas para monitoração dinâmica e são obviamente construídas de forma muito mais rápida e prática das que fazem balanceamento através de medições de recursos. Esta técnica foi criada antes mesmo de existirem computadores e é até hoje utilizada em larga escala por inúmeros sistemas com diferentes propósitos. . Com exceção do algoritmo RR, FIFO e escalonamento garantido, todos os outros sofrem do problema de Inanição (starvation), preemptivo;


6 -> Múltiplas Filas: São usadas várias filas de processos prontos para executar, cada processo é colocado em uma fila, e cada fila tem uma política de escalonamento própria e outra entre filas, preemptivo, cada fila tem um determinado nível de prioridade, sendo um dos mais antigos agendadores de prioridade, estava presente no CTSS (Compatible Time-Sharing System - Sistema Compatível de Divisão por Tempo).No algoritmo de Múltiplas Filas, também pode ser aplicado particularmente, em cada fila, diferentes algoritmos como por exemplo, o algoritmo RR ou FCFS.

Entre outros:

7 -> Algoritmo Loteria: O Sistema Operacional distribui tokens (fichas), numerados entre os processos, para o escalonamento é sorteado um numero aleatório para que o processo ganhe a vez na CPU, processos com mais tokens têm mais chance de receber antes a CPU;
Algoritmo de Prioridade: Como o próprio nome já diz, é um algoritmo onde cada processo no estado de pronto recebe uma prioridade, os processos com maiores prioridades são executados primeiro, prioridades que podem ser atribuídas dinâmica ou estaticamente. É um Algoritmo preemptivo.

8 -> Escalonamento garantido: Este algoritmo busca cumprir promessas de alocação de CPU o mais preciso possível. Uma forma completamente diferente de tratar a questão do escalonamento é fazer certas promessas ao usuário a respeito da performance, e cumpri-las de alguma forma.Uma promessa bem realista e muito fácil de cumprir é a de que se houver N usuários ativos na rede, cada um vai receber em torno de 1/N da capacidade de processamento que um usuário usou para todos os seus processos desde o momento em que tal usuário tornou-se ativo.Como o tempo que cada usuário gastou até o momento é conhecido, é fácil calcular a razão entre o tempo realmente concedido ao usuário e o tempo prometido. A ideia do algoritmo é por para rodar o processo com razões mais baixas, diminuindo, em consequência, as razões mais altas.

9 -> Algoritmo Fair-Share: O escalonamento é feito considerando o dono dos processos, onde cada usuário recebe uma fração da CPU e processos são escalonados procurando garantir essa fração. Se um usuário A possui mais processos que um usuário B e os dois têm a mesma prioridade, os processos de A serão mais demorados que os do B.

Todos os algoritmos classificam os processos em estados: Iniciando, Pronto, Executando, Entrada/ Saída e Terminado.


