#include <iostream>
#include <vector>
#include <memory>
#include <cassert>


/////// Primitives //////////

struct Z
{
    static const long long arguments_number = 1;

    static const long long result(std::vector < long long > const & arguments)
    {
        //assert(arguments.size() == arguments_number);
        return 0;
    }
};

struct N
{
    static const long long arguments_number = 1;

    static const long long result(std::vector < long long > const & arguments)
    {
        assert(arguments.size() == arguments_number);
        return arguments[0] + 1;
    }
};

template < int n, int k >
struct U
{
    static const long long arguments_number = n;

    static const long long result(std::vector < long long > const & arguments)
    {
        assert(k <= arguments_number && arguments_number == arguments.size());
        return arguments[k - 1];
    }
};

template < typename f, typename g, typename ... other_g >
struct S
{
    static const long long arguments_number = g::arguments_number;
    static const long long g_functions_number = (sizeof ... (other_g)) + 1;

    static const long long result(std::vector < long long > const & arguments)
    {
        assert(f::arguments_number == g_functions_number && arguments.size() == arguments_number);
        return f::result((std::vector < long long >) {g::result(arguments), other_g::result(arguments) ...});
    }
};

template < typename f, typename g >
struct R
{
    static const long long arguments_number = f::arguments_number + 1;

    static const long long result(std::vector < long long > const & arguments)
    {
        assert(arguments_number + 1 == g::arguments_number && arguments_number == arguments.size());

        std::vector < long long > new_arguments(arguments.begin(), arguments.end() - 1);
        if (arguments.back())
        {
            new_arguments.push_back(arguments.back() - 1);
            new_arguments.push_back(R< f, g>::result(new_arguments));
            return g::result(new_arguments);
        }
        else
            return f::result(new_arguments);
    }
};

template < typename f >
struct M {
    static const long long arguments_number = f::arguments_number - 1;

    static const long long result(std::vector < long long > const & arguments)
    {
        assert(arguments_number == arguments.size());
        std::vector < long long > new_arguments = arguments; new_arguments.push_back(0);
        for (long long y = 0; f::result(new_arguments); ++y)
            new_arguments[arguments_number] = y;
        return new_arguments[arguments_number];
    }
};


///////////////////////////// Functions /////////////////////////////

///////////// REAL PART ////////////

// 1 - number one
    typedef S< N, Z > one;

// ++number
    typedef S< N, U< 3, 3 > > inc;

// additional
    typedef R< U< 1, 1 >, inc > sum;

// multiplication
    typedef R< Z, S< sum, U< 3, 1 >, U< 3, 3 > > > mul;

// power
    typedef R< one, S< mul, U< 3, 1 >, U< 3, 3 > > > pow;

// factorial
    typedef R< one, S< mul, U< 3, 3 >, S< N, U< 3, 2 > > > > fac_from_two_arguments;
    typedef S< fac_from_two_arguments, U<1, 1>, U< 1, 1 > > fac;

// --number
    typedef S< R< Z, U< 3, 2 > >, U< 1, 1 >, U< 1, 1 > > dec;

// subtraction
    typedef R< U< 1, 1 >, S< dec, U< 3, 3 > > > sub;

// division
    typedef S< dec, S< M< S< sub, U< 3, 1 >, S< mul, U< 3, 2 >, U< 3, 3 > > > >, S< N, U< 2, 1 > >, U< 2, 2 > > > division;

// module number
    typedef S< sub, U< 2, 1 >, S< mul, U< 2, 2 >, division > > module;

///////////// LOGICAL PART ////////////

// not = no no
    typedef S < R< N, S< Z, U< 3, 3> > >, U< 1, 1 >, U< 1, 1 > > no_no;

// logical and = and_and
    typedef S< S< no_no, no_no >, mul > and_and;

// logical or = or_or
    typedef S< S< no_no, no_no>, sum > or_or;

// strong more. return (first arg > second arg)
    typedef S< S< no_no, no_no >, sub > is_more;

// strong less.return (first arg < second arg)
    typedef S < S< no_no, no_no >, S< sub, U< 2, 2 >, U< 2, 1 > > > is_less;

// soft more. return (first arg >= second arg)
    typedef S < no_no, is_less > no_less;

// soft less.return (first arg <= second arg)
    typedef S < no_no, is_more > no_more;

// equal =
    typedef S< and_and, no_more, no_less > equal;

// IF
    typedef S< sub, S< sum, U< 3, 3 >, S< mul, S< S< no_no, no_no>, U< 3, 1> >, U< 3, 2 > > >, S< mul, S< S< no_no, no_no>, U< 3, 1> >, U< 3, 3 > > > if_if;

// if division without mod then 1; else 0
    typedef S< equal, module, Z > is_div;

///////////// REAL PART ////////////

// plog
    typedef S< dec, M< S< is_div, U< 3, 2 >, S< pow, U< 3, 1 >, U< 3, 3> > > > > plog;

// is prime
typedef S<
            R< S< is_more, U< 1, 1 >, one >,
            S< S< and_and, S< module, U< 3, 1 >, U< 3, 2 > >, U< 3, 3 > >, U< 3, 1 >, S< S< N, N >, U< 3, 2 > >, U< 3, 3 > > >,
            U< 1, 1 >,
            S< dec, S< division, U< 1, 1 >, S< N, one > > >
        >  is_prime;

// previous prime number
typedef S< R< Z, S< sum, S< is_prime, U< 3, 2 > >, U< 3, 3 > > >, U< 1, 1 >, S< N, U< 1, 1 > > > prev_prime;

// nth prime
typedef S< M< S< is_more, U< 2, 1 >, S< prev_prime, U< 2, 2 > > > >, N > nth_prime;

struct tests
{

    template < typename f, typename ... T >
    static void print(T ... x)
    {
        std::vector< long long > arguments = { x ... };
        std::cout << f::result(arguments) << std::endl;
    }

    static void function_sum(long long x, long long y)
    {
        std::cout << x << " + " << y << " = ";
        tests::print<sum>(x, y);
    }

    static void function_sub(long long x, long long y)
    {
        std::cout << x << " - " << y << " = ";
        tests::print<sub>(x, y);
    }

    static void function_mul(long long x, long long y)
    {
        std::cout << x << " * " << y << " = ";
        tests::print<mul>(x, y);
    }

    static void function_div(long long x, long long y)
    {
        std::cout << x << " / " << y << " = ";
        tests::print<division>(x, y);
    }

    static void function_mod(long long x, long long y)
    {
        std::cout << x << " % " << y << " = ";
        tests::print<module>(x, y);
    }

    static void function_pow(long long x, long long y)
    {
        std::cout << x << " ^ " << y << " = ";
        tests::print<pow>(x, y);
    }

    static void function_fac(long long x)
    {
        std::cout << x << "! = ";
        tests::print<fac>(x);
    }

    static void function_plog(long long p, long long x)
    {
        std::cout << x << " mod " << p << " ^ r == 0. r : " << x << " mod " << p << " ^ (r + 1) != 0. r = ";
        tests::print<plog>(p, x);
    }

    static void function_is_less(long long x, long long y)
    {
        std::cout << x << " < " << y << " : ";
        tests::print<is_less>(x, y);
    }

    static void function_no_more(long long x, long long y)
    {
        std::cout << x << " <= " << y << " : ";
        tests::print<no_more>(x, y);
    }

    static void function_is_more(long long x, long long y)
    {
        std::cout << x << " > " << y << " : ";
        tests::print<is_more>(x, y);
    }

    static void function_no_less(long long x, long long y)
    {
        std::cout << x << " >= " << y << " : ";
        tests::print<no_less>(x, y);
    }

    static void function_equal(long long x, long long y)
    {
        std::cout << x << " == " << y << " : ";
        tests::print<equal>(x, y);
    }

    static void function_if(long long c, long long x, long long y)
    {
        std::cout << "if (" << c << ", "  << x << ", " << y << ") then ";
        tests::print<if_if>(c, x, y);
    }

    static void function_is_div(long long x, long long y)
    {
        std::cout << x << " mod " << y << " == 0 : ";
        tests::print<is_div>(x, y);
    }

    static void function_is_prime(long long x)
    {
        std::cout << x << " is prime : ";
        tests::print<is_prime>(x);
    }

    static void function_nth_prime(long long x)
    {
        std::cout << x << "th prime number : ";
        tests::print<nth_prime>(x);
    }

    static void show()
    {
       function_sum(12, 5);
       function_sum(3, 3);

       function_sub(10, 7);

       function_mul(5, 7);

       function_div(20, 5);
       function_div(20, 6);

       function_mod(20, 5);
       function_mod(20, 6);

       function_pow(5, 4);
       function_pow(2, 10);

       function_fac(0);
       function_fac(5);
       function_fac(8);


       function_is_less(10, 10);
       function_is_less(7, 10);
       function_is_less(12, 10);


       function_no_more(10, 10);
       function_no_more(7, 10);
       function_no_more(12, 10);

       function_is_more(10, 10);
       function_is_more(7, 10);
       function_is_more(12, 10);

       function_no_less(10, 10);
       function_no_less(7, 10);
       function_no_less(12, 10);

       function_equal(20, 20);
       function_equal(25, 20);

       function_if(0, 10, 20);
       function_if(1, 10, 20);

       function_is_div(20, 4);
       function_is_div(20, 3);

       function_plog(3, 12);
       function_plog(5, 125);
       function_plog(2, 15);

       function_is_prime(1);
       function_is_prime(2);
       function_is_prime(3);
       function_is_prime(15);
       function_is_prime(17);

       for (int i = 0; i < 12; ++i)
           function_nth_prime(i);

    }
};

	int main()
{
    tests::show();
    return 0;
}

