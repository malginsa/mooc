"""
Loyd's Fifteen puzzle - solver and visualizer
Note that solved configuration has the blank (zero) tile in upper left
Use the arrows key to swap this tile with its neighbors
"""

#import poc_fifteen_gui

class Puzzle:
	"""
	Class representation for the Fifteen puzzle
	"""

	def __init__(self, puzzle_height, puzzle_width, initial_grid=None):
		"""
		Initialize puzzle with default height and width
		Returns a Puzzle object
		"""
		self._height = puzzle_height
		self._width = puzzle_width
		self._grid = [[col + puzzle_width * row
					   for col in range(self._width)]
					  for row in range(self._height)]

		if initial_grid != None:
			for row in range(puzzle_height):
				for col in range(puzzle_width):
					self._grid[row][col] = initial_grid[row][col]

	def __str__(self):
		"""
		Generate string representaion for puzzle
		Returns a string
		"""
		ans = ""
		for row in range(self._height):
			ans += str(self._grid[row])
			ans += "\n"
		return ans

	#####################################
	# GUI methods

	def get_height(self):
		"""
		Getter for puzzle height
		Returns an integer
		"""
		return self._height

	def get_width(self):
		"""
		Getter for puzzle width
		Returns an integer
		"""
		return self._width

	def get_number(self, row, col):
		"""
		Getter for the number at tile position pos
		Returns an integer
		"""
		return self._grid[row][col]

	def set_number(self, row, col, value):
		"""
		Setter for the number at tile position pos
		"""
		self._grid[row][col] = value

	def clone(self):
		"""
		Make a copy of the puzzle to update during solving
		Returns a Puzzle object
		"""
		new_puzzle = Puzzle(self._height, self._width, self._grid)
		return new_puzzle

	########################################################
	# Core puzzle methods

	def current_position(self, solved_row, solved_col):
		"""
		Locate the current position of the tile that will be at
		position (solved_row, solved_col) when the puzzle is solved
		Returns a tuple of two integers		
		"""
		solved_value = (solved_col + self._width * solved_row)

		for row in range(self._height):
			for col in range(self._width):
				if self._grid[row][col] == solved_value:
					return (row, col)
		assert False, "Value " + str(solved_value) + " not found"

	def update_puzzle(self, move_string):
		"""
		Updates the puzzle state based on the provided move string
		"""
		zero_row, zero_col = self.current_position(0, 0)
		for direction in move_string:
			if direction == "l":
				assert zero_col > 0, "move off grid: " + direction
				self._grid[zero_row][zero_col] = self._grid[zero_row][zero_col - 1]
				self._grid[zero_row][zero_col - 1] = 0
				zero_col -= 1
			elif direction == "r":
				assert zero_col < self._width - 1, "move off grid: " + direction
				self._grid[zero_row][zero_col] = self._grid[zero_row][zero_col + 1]
				self._grid[zero_row][zero_col + 1] = 0
				zero_col += 1
			elif direction == "u":
				assert zero_row > 0, "move off grid: " + direction
				self._grid[zero_row][zero_col] = self._grid[zero_row - 1][zero_col]
				self._grid[zero_row - 1][zero_col] = 0
				zero_row -= 1
			elif direction == "d":
				assert zero_row < self._height - 1, "move off grid: " + direction
				self._grid[zero_row][zero_col] = self._grid[zero_row + 1][zero_col]
				self._grid[zero_row + 1][zero_col] = 0
				zero_row += 1
			else:
				assert False, "invalid direction: " + direction

	##################################################################
	# Phase one methods

	def lower_row_invariant(self, target_row, target_col):
		"""
		Check whether the puzzle satisfies the specified invariant
		at the given position in the bottom rows of the puzzle (target_row > 1)
		Returns a boolean
		"""
		if self.current_position(0, 0) != (target_row, target_col):
			return False
		for col in range(target_col + 1, self._width):
			if self.current_position(target_row, col) != (target_row, col):
				return False
		for row in range(target_row + 1, self._height):
			for col in range(self._width):
				if self.current_position(row, col) != (row, col):
					return False 
		return True

	def position_tile(self, srcrow, srccol, dstrow, dstcol):
		"""
		Helper function for moving tile from (srcrow, srccol) to (dstrow, dstcol).
		Only for solve_interior_tile, solve_col0_tile methods.
		(dstrow, dstcol) must be occupied by zero.
		Returns a 'move' string.
		"""
		ret = ''
		if (srcrow, srccol) == (dstrow, dstcol):
			return ''
		if srccol > dstcol:
			# bring tile from right conner to the same col, zero placed to the left of the tile
			ret += 'u' * (dstrow - srcrow) + 'r' * (srccol - dstcol - 1)
			if srcrow == 0: # zero cycles behind tile
				ret += 'rdllu' * (srccol - dstcol)
			else: # zero cycles above tile
				ret += 'rulld' * (srccol - dstcol)
		elif srccol < dstcol:
			# bring tile from left conner to the same col, zero placed to the left of the tile
			ret += 'u' * (dstrow - srcrow) + 'l' * (dstcol - srccol - 1)
			if srcrow == 0: # zero cycles behind target
				ret += 'ldrru' * (dstcol - srccol - 1) + 'l'
			else: # zero cycles above target
				ret += 'lurrd' * (dstcol - srccol - 1) + 'l'
		else:
			ret += 'l' + 'u' * (dstrow - srcrow)
		for dummy_i in range(dstrow - srcrow):
			# lift tile down to dst, zero placed to the left of the tile
			ret += 'druld'
		return ret

	def solve_interior_tile(self, target_row, target_col):
		"""
		Place correct tile at target position
		Updates puzzle and returns a move string
		"""
		(currow, curcol) = self.current_position(target_row, target_col)
		ret = self.position_tile(currow, curcol, target_row, target_col)
		self.update_puzzle(ret)
		return ret

	def solve_col0_tile(self, target_row):
		"""
		Solve tile in column zero on specified row (> 1)
		Updates puzzle and returns a move string
		"""
		self.update_puzzle('ur')
		(currow, curcol) = self.current_position(target_row, 0)
		# Are we lucky?
		if (currow, curcol) == (target_row, 0):
			# move zero to the end of target_row
			self.update_puzzle('r' * (self._width - 2))
			return 'ur' + 'r' * (self._width - 2)
		# move tile to position 3x2
		ret = self.position_tile(currow, curcol, target_row - 1, 1)
		# add move string for 3x2 puzzle
		ret += 'ruldrdlurdluurddlur'
		# move zero to the end of target_row
		ret += 'r' * (self._width - 2)
		self.update_puzzle(ret)
		ret = 'ur' + ret
		return ret

	#############################################################
	# Phase two methods

	def row0_invariant(self, target_col):
		"""
		Check whether the puzzle satisfies the row zero invariant
		at the given column (col > 1)
		Returns a boolean
		"""
		if self.current_position(0, 0) != (0, target_col):
			return False
		if self.current_position(1, target_col) != (1, target_col):
			return False
		for row in range(self._height):
			for col in range(self._width):
				if (row > 1) or (col > target_col):
					if self.current_position(row, col) != (row, col):
						return False
		return True

	def row1_invariant(self, target_col):
		"""
		Check whether the puzzle satisfies the row one invariant
		at the given column (col > 1)
		Returns a boolean
		"""
		if self.current_position(0, 0) != (1, target_col):
			return False
		for row in range(self._height):
			for col in range(self._width):
				if (row > 1) or (col > target_col):
					if self.current_position(row, col) != (row, col):
						return False
		return True

	def solve_row0_tile(self, target_col):
		"""
		Solve the tile in row zero at the specified column
		Updates puzzle and returns a move string
		"""
		self.update_puzzle('ld')
		(currow, curcol) = self.current_position(0, target_col)
		# Are we lucky?
		if (currow, curcol) == (0, target_col):
			# move zero to the end of target_row
			return 'ld'
		# move tile to position 2x3
		ret = self.position_tile(currow, curcol, 1, target_col - 1)
		# add move string for 2x3 puzzle
		ret += 'urdlurrdluldrruld'
		self.update_puzzle(ret)
		ret = 'ld' + ret
		return ret

	def solve_row1_tile(self, target_col):
		"""
		Solve the tile in row one at the specified column
		Updates puzzle and returns a move string
		"""
		(srcrow, srccol) = self.current_position(1, target_col)
		ret = self.position_tile(srcrow, srccol, 1, target_col)
		# move zero from the left position to above of target
		ret += 'ur'
		self.update_puzzle(ret)
		return ret

	###########################################################
	# Phase 3 methods

	def solve_2x2(self):
		"""
		Solve the upper left 2x2 part of the puzzle
		Updates the puzzle and returns a move string
		"""
		ret = ''
		while self.current_position(0, 0) != (0, 0) \
				or self.current_position(1, 1) != (1, 1):
			if self.current_position(0, 0) == (0, 0):
				ret += 'r'
				self.update_puzzle('r')
			elif self.current_position(0, 0) == (0, 1):
				ret += 'd' 			
				self.update_puzzle('d')
			elif self.current_position(0, 0) == (1, 1):
				ret += 'l'
				self.update_puzzle('l')
			elif self.current_position(0, 0) == (1, 0):
				ret += 'u'
				self.update_puzzle('u')
			else:
				assert False, 'zero not found in 2x2 part of the puzzle'
		return ret

	def solve_puzzle(self):
		"""
		Generate a solution string for a puzzle
		Updates the puzzle and returns a move string
		"""
		ret = ''
		# solve bottom m-2 rows
		ret += self.move_zero_to_bootom_right()
		for row in range(self._height - 1, 1, -1):
			for col in range(self._width - 1, 0, -1):
				assert self.lower_row_invariant(row, col), 'invariant is broken in (' + row + ',' + col + ')'
				ret += self.solve_interior_tile(row, col)
			assert self.lower_row_invariant(row, 0), 'invariant is broken in (' + row + ', 0)'
			ret += self.solve_col0_tile(row)
		assert self.lower_row_invariant(1, self._width - 1), 'invariant is broken in (1,' + (self._width) + ')'
		# phase 2: solve_row0_tile, solve_row1_tile methods
		for col in range(self._width - 1, 1, -1):
			assert self.row1_invariant(col), 'row1 vari-ant'
			ret += self.solve_row1_tile(col)
			assert self.row0_invariant(col), 'row0 vari-ant'
			ret += self.solve_row0_tile(col)
		# phase 3
		self.solve_2x2()
		return ret

	def move_zero_to_bootom_right(self):
		"""
		Bring zero to the bootom-right corner.
		Updates the puzzle and returns a move string.
		"""
		ret = ''
		(zerorow, zerocol) = self.current_position(0, 0)
		ret += 'd' * (self._height - zerorow - 1)
		ret += 'r' * (self._width - zerocol - 1)
		self.update_puzzle(ret)
		return ret

def test_1_phase():
	# test lower_row_invariant method
	puzzle = Puzzle(4, 4)
	assert puzzle.lower_row_invariant(0, 0), 'lower_row_invariant fails'
	assert not puzzle.lower_row_invariant(1, 2), 'lower_row_invariant fails'
	puzzle = Puzzle(4, 4, [[9,1,5,10], [4,8,2,7], [3,6,0,11], [12,13,14,15]])
	assert puzzle.lower_row_invariant(2, 2), 'lower_row_invariant fails'
	assert not puzzle.lower_row_invariant(1, 1), 'lower_row_invariant fails'
	assert not puzzle.lower_row_invariant(3, 2), 'lower_row_invariant fails'
	puzzle = Puzzle(4, 4, [[9,1,5,10], [4,8,2,7], [3,6,15,11], [12,13,14,0]])
	assert puzzle.lower_row_invariant(3, 3), 'lower_row_invariant fails'
	# test solve_2x2 method
	puzzle = Puzzle(4, 4)
	puzzle.update_puzzle('r')
	assert puzzle.solve_2x2() == 'dlurdlurdlu', 'bug in solve_2x2'
	# test solve_interior_tile method
	puzzle = Puzzle(4, 4, [[5,6,0,7], [8,1,15,11], [10,3,2,4], [9,12,14,13]])
	print "ORIGINAL:"
	print puzzle
	path = puzzle.move_zero_to_bootom_right()
	print "zero to bottom-right:" + path
	print puzzle
	path = puzzle.solve_interior_tile(3, 3)
	print "bring 15 to his place: " + path
	print puzzle
	path = puzzle.solve_interior_tile(3, 2)
	print "bring 14 to his place: " + path
	print puzzle
	path = puzzle.solve_interior_tile(3, 1)
	print "bring 13 to his place: " + path
	print puzzle
	# test solve_col0_tile method
	path = puzzle.solve_col0_tile(3)
	print "bring 12 to his place: " + path
	print puzzle

def test_2_phase():
	puzzle = Puzzle(4, 4, [[5,6,0,7], [8,1,15,11], [10,3,2,4], [9,12,14,13]])
	print "ORIGINAL:\n", puzzle
	path = puzzle.solve_puzzle()
	print 'solve for the bottom rows: ', path
	print puzzle

	assert puzzle.row1_invariant(3), 'row1 vari-ant'
	path = puzzle.solve_row1_tile(3)
	print 'solve for the 1st row: ', path
	print puzzle

	assert puzzle.row0_invariant(3), 'row0 vari-ant'
	path = puzzle.solve_row0_tile(3)
	print 'solve for the 0st row: ', path
	print puzzle

def test_1_2_phases():
	puzzle = Puzzle(4, 4, [[5,6,0,7], [8,1,15,11], [10,3,2,4], [9,12,14,13]])
	print "ORIGINAL:\n", puzzle
	path = puzzle.solve_puzzle()
	print 'solve for the bottom rows: ', path
	print puzzle

# Start interactive simulation
#poc_fifteen_gui.FifteenGUI(Puzzle(4, 4))
# selftest()
test_1_2_phases()

