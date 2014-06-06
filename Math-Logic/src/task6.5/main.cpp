#include <iostream>
#include <vector>
#include <memory>
#include <cassert>
#include <cmath>
#include <cstdio>

/////// Primitives //////////

struct Z
{
    static const unsigned long long  arguments_number = 1;

    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        //assert(arguments.size() == arguments_number);
        return 0;
    }
};

struct N
{
    static const unsigned long long  arguments_number = 1;

    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
    //    assert(arguments.size() == arguments_number);
        return arguments[0] + 1;
    }
};

template < int n, int k >
struct U
{
    static const unsigned long long  arguments_number = n;

    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        //assert(k <= arguments_number && arguments_number == arguments.size());
        return arguments[k - 1];
    }
};

template < typename f, typename g, typename ... other_g >
struct S
{
    static const unsigned long long  arguments_number = g::arguments_number;
    static const unsigned long long g_functions_number = (sizeof ... (other_g)) + 1;

    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        //assert(f::arguments_number == g_functions_number && arguments.size() == arguments_number);
        return f::result((std::vector < unsigned long long  >) {g::result(arguments), other_g::result(arguments) ...});
    }
};

template < typename f, typename g >
struct R
{
    static const unsigned long long  arguments_number = f::arguments_number + 1;

    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
      //  assert(arguments_number + 1 == g::arguments_number && arguments_number == arguments.size());

        std::vector < unsigned long long  > new_arguments(arguments.begin(), arguments.end() - 1);
        if (arguments.back())
        {
            new_arguments.push_back(arguments.back() - 1);
            new_arguments.push_back(R<f, g>::result(new_arguments));
            return g::result(new_arguments);
        }
        else
            return f::result(new_arguments);
    }
};

template < typename f >
struct M
{
    static const unsigned long long  arguments_number = f::arguments_number - 1;

    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        std::vector < unsigned long long  > new_arguments = arguments; new_arguments.push_back(0);
        for (unsigned long long y = 0; f::result(new_arguments); ++y)
            new_arguments[arguments_number] = y;
        return new_arguments[arguments_number];
    }
};


///////////////////////////// Functions /////////////////////////////

/////// STRUCTURES FOR FAST CALC /////////////////

struct sum_func
{
    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        assert(arguments.size() == 2);
        return arguments[0] + arguments[1];
    }
};

struct sub_func
{
    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        assert(arguments.size() == 2);
        if (arguments[0] >= arguments[1])
            return arguments[0] - arguments[1];
        else
            return 0;
    }
};

struct mul_func
{
    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        assert(arguments.size() == 2);
        return arguments[0] * arguments[1];
    }
};

struct pow_func
{
    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        assert(arguments.size() == 2 && arguments[1] >= 0);
        return (unsigned long long)pow((double)arguments[0], (double)arguments[1]);
    }
};

struct div_func
{
    static const unsigned long long result(std::vector < unsigned long long  > const & arguments)
    {
        assert(arguments.size() == 2 && arguments[1] != 0);
        return arguments[0] / arguments[1];
    }
};

struct mod_func
{
    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        assert(arguments.size() == 2 && arguments[1] != 0);
        return arguments[0] % arguments[1];
    }
};


struct plog_func
{
    static const unsigned long long  result(std::vector < unsigned long long  > const & arguments)
    {
        assert(arguments.size() == 2 && arguments[0] > 1);
        unsigned long long ans = 1;
        int ind = 0;
        while (arguments[1] % ans == 0)
        {
            ++ind;
            ans *= arguments[0];
        }
        return --ind;
    }
};


struct equal_func
{
    static const unsigned long long result(std::vector < unsigned long long > const & arguments)
    {
        assert(arguments.size() == 2);
        if (arguments[0] == arguments[1])
            return 1;
        else
            return 0;
    }
};

///////////// REAL PART ////////////


// numbers 1 - 5
    typedef S< N, Z > ONE;
    typedef S< N, ONE > TWO;

// --number
    typedef S< R< Z, U< 3, 2 > >, U< 1, 1 >, U< 1, 1 > > DEC;

// additional
    typedef S< sum_func, U<2, 1>, U<2, 2> > SUM;

// subtraction
    typedef S< sub_func, U<2, 1>, U<2, 2> > SUB;

// multiplication
    typedef S< mul_func, U<2, 1>, U<2, 2> > MUL;

// power
    typedef S< pow_func, U<2, 1>, U<2, 2> > POW;

// division
    typedef S< div_func, U<2, 1>, U<2, 2> > DIV;

// module number
    typedef S< mod_func, U<2, 1>, U<2, 2> > MOD;

///////////// LOGICAL PART ////////////

// not
    typedef S < R< N, S< Z, U< 3, 3 > > >, U< 1, 1 >, U< 1, 1 > > NOT;

// logical and
    typedef S< S< NOT, NOT >, MUL > AND;

// logical or
    typedef S< S< NOT, NOT >, SUM > OR;

// strong more. return (first arg > second arg)
    typedef S< S< NOT, NOT >, SUB > IS_MORE;

// strong less.return (first arg < second arg)
    typedef S < S< NOT, NOT >, S< SUB, U< 2, 2 >, U< 2, 1 > > > IS_LESS;

// soft more. return (first arg >= second arg)
    typedef S < NOT, IS_LESS > NO_LESS;

// soft less.return (first arg <= second arg)
    typedef S < NOT, IS_MORE > NO_MORE;

// equal =
    typedef S< equal_func, U<2, 1>, U<2, 2> > EQUAL;

// IF (0, x, y) return y. if (z > 0, x, y) return x
    typedef S< SUB,
               S< SUM, U< 3, 3 >, S< MUL, S< S< NOT, NOT >, U< 3, 1> >, U< 3, 2 > > >,
               S< MUL, S< S< NOT, NOT >, U< 3, 1> >, U< 3, 3 > >
            > IF;


// if division without mod then 1; else 0
    typedef S< EQUAL, MOD, Z > IS_DIV;

// plog
    typedef S< DEC, M< S< IS_DIV, U< 3, 2 >, S< POW, U< 3, 1 >, U< 3, 3> > > > > PLOG;

// is prime
typedef S<
            R< S< IS_MORE, U< 1, 1 >, ONE >,
            S< S< AND, S< MOD, U< 3, 1 >, U< 3, 2 > >, U< 3, 3 > >, U< 3, 1 >, S< S< N, N >, U< 3, 2 > >, U< 3, 3 > > >,
            U< 1, 1 >,
            S< DEC, S< DIV, U< 1, 1 >, S< N,ONE > > >
        >  IS_PRIME;

// previous prime number
typedef S< R< Z, S< SUM, S< IS_PRIME, U< 3, 2 > >, U< 3, 3 > > >, U< 1, 1 >, S< N, U< 1, 1 > > > PREV_PRIME;

// nth prime
typedef S< M< S< IS_MORE, U< 2, 1 >, S< PREV_PRIME, U< 2, 2 > > > >, N > NTH_PRIME;


///////////////////// ACCERMAN FUNCTION START ////////////////////

//////// STACK PARAMETERS ///////////
typedef S< TWO, U<2, 1> > TWO2;

typedef S< DEC, S< PLOG, TWO, U< 1, 1 > > > STACK_SIZE;

typedef S< MUL,
           U< 2, 1 >,
           S< POW,
              S< NTH_PRIME,
                 S< N, S< STACK_SIZE, U< 2, 1 > > >
              >,
              S< N, U< 2, 2 > >
           >
        > PUSH_WITHOUT_CHANGE_STACK;

typedef S< MUL, TWO2, PUSH_WITHOUT_CHANGE_STACK > PUSH;

typedef S< PUSH, S< PUSH, U<3, 1>, U<3, 2> >, U<3, 3> >  PUSH2;

typedef S< PUSH, S< PUSH, S< PUSH, U<4, 1>, U<4, 2> >, U<4, 3> >, U<4, 4> >  PUSH3;

typedef S< DEC, S< PLOG, S< NTH_PRIME, STACK_SIZE >, U< 1, 1 > > > TOP;

typedef S< DEC, S< PLOG, S< NTH_PRIME, S< DEC, STACK_SIZE > >, U< 1, 1 > > > TOP2;

typedef S< DIV, S< DIV, U< 1, 1 >, S< POW, S< NTH_PRIME,  STACK_SIZE >, S< N, TOP > > >, TWO > POP;

typedef S< POP, S< POP, U<1, 1> > > POP2;

////////////// ACKERMANN PARAMETERS /////////////

typedef S< PUSH, POP2, S< N, TOP> > FIRST;
typedef S< PUSH2, POP2, S< DEC, TOP2 >, ONE> SECOND;
typedef S< PUSH3, POP2, S< DEC, TOP2 >, TOP2, S< DEC, TOP> > THIRD;
typedef S< IF, S< EQUAL, TOP, Z >, SECOND, THIRD > OTHER;
typedef S< IF, S< EQUAL, TOP2, Z >, FIRST, OTHER > ONE_ACKERMANN_STEP;

typedef S< PUSH2, TWO, U<2, 1>, U<2, 2> > INIT;

typedef R< INIT, S< ONE_ACKERMANN_STEP, U<4,4> > > ACKERMANN_STEPS; // return stack after k steps

typedef S< STACK_SIZE, S< ACKERMANN_STEPS, U<3, 1>, U<3, 2>, U<3, 3> > > STACK_SIZE_AFTER_STEPS; // return stack size

typedef M< S< SUB, S< STACK_SIZE_AFTER_STEPS, U<3, 1>, U<3, 2>, U<3, 3> >, ONE > > NUMBER_OF_STEPS; // return k : after k steps know result

typedef S< TOP, S< ACKERMANN_STEPS, U<3,1>, U<3,2>, S< NUMBER_OF_STEPS, U<3, 1>, U<3, 2> > > > ACKERMANN; // YES YES YES WORKINGGG!!!!!

struct tests
{

    template < typename f, typename ... T >
    static unsigned long long print(T ... x)
    {
        std::vector< unsigned long long  > arguments = {(unsigned long long) x ... };
        unsigned long long tmp = f::result(arguments);
        std::cout << tmp << std::endl;
        return tmp;
    }

    static unsigned long long function_ackermann(unsigned long long m, unsigned long long n)
    {
        std::cout << "A(" << m << ", " << n << ") = ";
        unsigned long long tmp = tests::print<ACKERMANN>(m, n);
        return tmp;
    }

    static void function_print_stack(unsigned long long n)
    {
        std::vector < unsigned long long > v = {3, 5, 7, 11, 13, 17, 19, 23};
        for (int i = 0; i < v.size() && n != 2; ++i) {
            int ind = 0;
            while (n % v[i] == 0)
            {
                ++ind;
                n /= v[i];
            }
            n /= 2;
            std::cout << --ind << " ";
        }
        std::cout << std::endl;
    }

    static void show()
    {
        for (int i = 0; i < 4; ++i)
                for (int j = 0; j < 4; ++j)
                   function_ackermann(i, j);
    }
};

int main()
{
    //freopen("output.txt", "w", stdout);
    tests::show();
    return 0;
}
