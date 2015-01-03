def generate_permutations (src):
  dst = [0 for i in range (0, len(src))]
  return generate_permutations_rec (src, dst)

def generate_permutations_rec (src, dst):
  if len (src) == 0:
    print 'rec_base:  dst = ',dst
    return dst
  n = len (dst)
  first = src.pop(0)
  i = 0
  while i < n:
    if dst[i] == 0:
      dst[i] = first
      src_new = src[:]
      dst_new = dst[:]
      generate_permutations_rec (src_new,dst_new)
      dst[i] = 0
    i += 1
  return

#A = generate_permutations ([1,1,2,2,3,4,5])
A = generate_permutations ([1,2,3,4])
