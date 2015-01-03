"""
Project 1 - Degree distributions for graphs
"""

EX_GRAPH0 = {0: set([1,2]), 1: set([]), 2: set([])}
EX_GRAPH1 = {0: set([1, 4, 5]), 1: set([2, 6]), 2: set([3]), \
             3: set([0]), 4: set([1]), 5: set([2]), 6: set([])}
EX_GRAPH2 = {0: set([1, 4, 5]), 1: set([2, 6]), 2: set([3, 7]), \
             3: set([7]), 4: set([1]), 5: set([2]), 6: set([]), \
             7: set([3]), 8: set([1, 2]), 9: set([0, 3, 4, 5, 6, 7])}

def make_complete_graph(num_nodes):
    """
    return a complete graph maked of num_nodes
    """
    graph = {}
    all_nodes = [node for node in range(num_nodes)]
    for node in range(num_nodes):
        neibs = all_nodes[:]
        neibs.remove(node)
        graph[node] = set(neibs)
    return graph

def compute_in_degrees(digraph):
    """
    compute indegrees for the nodes in the digraph
    """
    indegrees = {}
    for key in digraph.keys():
        indegrees[key] = 0
    for node in digraph.keys():
        for neib in digraph[node]:
            indegrees[neib] += 1
    return indegrees

def in_degree_distribution(digraph):
    """
    compute unnormalized distribution
    """
    indegrees = compute_in_degrees(digraph)
    distr = {}
    for indegree in set(indegrees.values()):
        distr[indegree] = 0
    for node in indegrees.keys():
        distr[indegrees[node]] += 1
    return distr

#print in_degree_distribution(EX_GRAPH2)
