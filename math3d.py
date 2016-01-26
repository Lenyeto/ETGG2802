import math


# @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# @ VECTORN class                    @
# @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
class VectorN(object):
    """ This is a class which will be used to represent a vector (or a point
        in n-dimensional space. It is basically acts as a python list (of
        floats), but with extra vector operations that python lists don't
        have. """


    def __init__(self, p):
        """ The constructor.  p can be one of these type of objects:
            an integer: the dimension this vector exist in (in which case,
                 this vector is initialized to a p-dimensional zero vector)
            a sequence-like object: the values of the vector (we infer
                 the dimension based on the length of the sequence.  The values
                 are copied to this VectorN (and converted to floats)) """
        if isinstance(p, int):
            self.mDim = p
            self.mData = [0.0] * p
        elif hasattr(p, "__len__") and hasattr(p, "__getitem__"):
            # Note: We're using len(p) and p[i], so we need the above
            #   two methods.
            self.mDim = len(p)
            self.mData = []
            for i in range(len(p)):
                self.mData.append(float(p[i]))
        else:
            raise TypeError("Invalid parameter.  You must pass a sequence or an integer")


    def __str__(self):
        """ Returns a string representation of this VectorN (self) """
        s = "<Vector" + str(self.mDim) + ": "
        s += str(self.mData)[1:-1]
        s += ">"
        return s


    def __len__(self):
        """ Returns the dimension of this VectorN when a VectorN is passed to
           the len function (which is built into python) """
        return self.mDim


    def __getitem__(self, index):
        """ Returns an element of mData """
        return self.mData[index]


    def __setitem__(self, index, value):
        """ Sets the value of self.mData[index] to value """
        self.mData[index] = float(value)    # Could fail with an invalid index
                                            # error, but we'll let python handle
                                            # it.
    def copy(self):
        """ Returns an identical (but separate) VectorN """
        # Note: This works because our VectorN has a __len__ and __getitem__
        #     method (which is what the constructor expects)
        return VectorN(self.mData)


    def __eq__(self, rhs):
        """ Returns True if this VectorN (self) is of the same type (VectorN)
            and dimension of rhs and all the values in self are the same as the
            values in rhs """
        if isinstance(rhs, VectorN) and self.mDim == rhs.mDim:
            for i in range(self.mDim):
                if self.mData[i] != rhs.mData[i]:
                    return False
            return True
        return False


    def iTuple(self):
        """ Returns a tuple with the values of this vector, converted to integers """
        L = []
        for val in self.mData:
            L.append(int(val))
        return tuple(L)     # Converts the *list* L to a tuple and returns it


    def __add__ (self, rhs):
        """ Adds two vectors together """
        if isinstance(rhs, VectorN):
            if self.mDim == rhs.mDim:
                L = []
                i = 0
                while i < self.mDim:
                    L.append(self.mData[i] + rhs.mData[i])
                    i += 1
                return VectorN(L)
            else:
                raise TypeError("You can only add another Vector6 to this Vector3")
        else:
            raise TypeError("You can only add another Vector3 to this Vector6")     
        
    
    def __sub__ (self, rhs):
        """ Subtracts two vectors from one another """
        if isinstance(rhs, VectorN):
            if self.mDim == rhs.mDim:
                L = []
                i = 0
                while i < self.mDim:
                    L.append(self.mData[i] - rhs.mData[i])
                    i += 1
                return VectorN(L)
            else:
                raise TypeError("You can only subtract another Vector6 from this Vector6")
        else:
            raise TypeError("You can only subtract another Vector3 from this Vector3")


    def __neg__ (self):
        """ Makes a single vector negative """
        L = []
        for value in self.mData:
            new_val = value * -1
            L.append(new_val)
        return VectorN(L)
        

    def __mul__ (self, lhs):
        """ Multiplies a vector by a scaler on the right side. (VectorN * x) """
        if isinstance(lhs, int):           
            L = []
            i = 0
            while i < self.mDim:
                L.append(self.mData[i] * lhs)
                i += 1
            return VectorN(L)
            
        else:
            raise TypeError("You can only multiply this Vector by a scalar")

    def __rmul__ (self, rhs):
        """ Multiplies a vector by a scaler on the left side. (x * VectorN) """
        if isinstance(rhs, int):           
            L = []
            i = 0
            while i < self.mDim:
                L.append(self.mData[i] * rhs)
                i += 1
            return VectorN(L)
            
        else:
            raise TypeError("You can only multiply a vector by a scaler.")

    def __truediv__ (self, rhs):
        """ Divides each value in a vector by a scaler """
        if isinstance(rhs, int):           
            L = []
            i = 0
            while i < self.mDim:
                L.append(self.mData[i] / rhs)
                i += 1
            return VectorN(L)
            
        else:
            raise NotImplementedError("You cannot divide anything by a VectorN")
        
    def __truerdiv__ (self, rhs):
        if isinstance(rhs, VectorN):           
            raise NotImplementedError("You cannot divide anything by a VectorN")

    def isZero (self):
        """ Returns true if all values in a vector are zeroes. Otherwise, returns false """
        for num in self.mData:
            if num != 0:
                return False
            else:
                return True

    def magnitude (self):
        """ Returns the magnitude of a scaler (square root of all values added together and squared """
        x = 0.0
        for value in self.mData:
            x += value ** 2
        
        return float(x ** 0.5)

    def normalized_copy (self):
        """ divides each value in a vector by the magnitude of the same vector """
        L = []
        magnitude = self.magnitude()
        for value in self.mData:
            L.append(value / magnitude)
        return VectorN(L)
            
        








if __name__ == "__main__":
    import pygame

    v = VectorN(5)
    print(v)                     # <Vector5: 0.0, 0.0, 0.0, 0.0, 0.0>
    w = VectorN((1.2, "3", 5))
    # q = VectorN(pygame.Surface((10,10)))    # Should raise an exception
    # q = VectorN((1.2, "abc", 5))            # Should raise an exception
    # q = VectorN("abc")                      # Should raise an exception

    print(w)                     # <Vector3: 1.2, 3.0, 5.0>
    z = w.copy()
    z[0] = 9.9
    z[-1] = "6"
    print(z)                     # <Vector3: 9.9, 3.0, 6.0>
    print(w)                     # <Vector3: 1.2, 3.0, 5.0>
    print(z == w)                # False
    print(z == VectorN((9.9, "3", 6)))   # True
    print(z == 5)                # False
    print(z[0])                  # 9.9
    print(len(v))                # 5
    print(w.iTuple())        # (1, 3, 5)
