package com.thyssenkrupp.tkse;

import static org.junit.Assert.*;

import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.junit.Test;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

import com.thyssenkrupp.tkse.GeoHashUtil;


public class GeoHashUtilTest {


	private final String STAINT_LOUIS_WKT = "POLYGON ((-90.25749206542969 38.71551876930462, -90.31723022460938 38.69301319283493, -90.3247833251953 38.64744452237617, -90.31997680664062 38.58306291549108, -90.27053833007812 38.55460931253295, -90.22109985351562 38.54601733154524, -90.15037536621094 38.55299839430547, -90.11123657226562 38.566421609878674, -90.08583068847656 38.63028174397134, -90.08583068847656 38.66996443163297, -90.0933837890625 38.718197532760165, -90.15243530273436 38.720876195817276, -90.25749206542969 38.71551876930462))";
	private final String COLUMBIA_WKT = "POLYGON ((-90.23345947265625 38.484769753492536, -90.25886535644531 38.47455675836861, -90.25886535644531 38.438530965643004, -90.23826599121092 38.40356337960024, -90.19088745117188 38.39818224865764, -90.16685485839844 38.435841752321856, -90.16891479492188 38.47616943274547, -90.23345947265625 38.484769753492536))";
	private final String ROUTE_WKT = "LINESTRING (-90.1499 38.33624 0, -90.1497 38.33627 0, -90.14935 38.3363 0, -90.14902 38.33635 0, -90.14914 38.33689 0, -90.14932 38.33759 0, -90.1494 38.33795 0, -90.14949 38.33848 0, -90.14959 38.33896 0, -90.14962 38.33916 0, -90.14969 38.3395 0, -90.14989 38.34063 0, -90.15015 38.34197 0, -90.1503 38.34297 0, -90.15034 38.3436 0, -90.15034 38.34409 0, -90.15037 38.3452 0, -90.1504 38.34553 0, -90.15041 38.34577 0, -90.15043 38.34597 0, -90.15057 38.34789 0, -90.15062 38.34906 0, -90.15063 38.34909 0, -90.15063 38.34944 0, -90.15064 38.3496 0, -90.15066 38.35026 0, -90.15068 38.35055 0, -90.15068 38.35062 0, -90.15072 38.3509 0, -90.15079 38.3512 0, -90.15109 38.35211 0, -90.15137 38.35288 0, -90.15182 38.3543 0, -90.15188 38.35445 0, -90.15207 38.35507 0, -90.15227 38.35566 0, -90.15229 38.3557 0, -90.15232 38.35575 0, -90.15235 38.35579 0, -90.15237 38.35583 0, -90.15239 38.35585 0, -90.15241 38.35588 0, -90.15249 38.35596 0, -90.15254 38.356 0, -90.1526 38.35604 0, -90.15264 38.35606 0, -90.15288 38.3562 0, -90.15314 38.35629 0, -90.1532 38.35639 0, -90.15328 38.35649 0, -90.15329 38.35653 0, -90.15331 38.35657 0, -90.15332 38.35663 0, -90.15329 38.35681 0, -90.15326 38.35704 0, -90.15314 38.35734 0, -90.15312 38.3574 0, -90.1531 38.35748 0, -90.15309 38.3575 0, -90.15309 38.35754 0, -90.15308 38.35759 0, -90.15307 38.35768 0, -90.15307 38.35795 0, -90.15308 38.35801 0, -90.15309 38.35815 0, -90.15311 38.35821 0, -90.15312 38.35827 0, -90.15315 38.35836 0, -90.15357 38.35982 0, -90.15369 38.36028 0, -90.15385 38.36081 0, -90.15387 38.36086 0, -90.15398 38.36125 0, -90.15405 38.36154 0, -90.15407 38.36165 0, -90.1541 38.36192 0, -90.15393 38.36349 0, -90.15393 38.3636 0, -90.15392 38.36375 0, -90.15392 38.3639 0, -90.15393 38.36405 0, -90.15397 38.36438 0, -90.154 38.36456 0, -90.15404 38.36472 0, -90.15411 38.36495 0, -90.1543 38.36539 0, -90.15434 38.36546 0, -90.15438 38.36555 0, -90.15444 38.36566 0, -90.1545 38.36575 0, -90.15456 38.36585 0, -90.15484 38.36627 0, -90.15586 38.36767 0, -90.15591 38.36775 0, -90.15629 38.36829 0, -90.15641 38.36848 0, -90.15646 38.36855 0, -90.15659 38.3688 0, -90.15657 38.36894 0, -90.15669 38.36922 0, -90.1568 38.36955 0, -90.15688 38.36986 0, -90.15692 38.37005 0, -90.15716 38.37141 0, -90.15724 38.37177 0, -90.15738 38.37231 0, -90.15748 38.3726 0, -90.15755 38.37278 0, -90.15759 38.3729 0, -90.15804 38.3739 0, -90.15942 38.37714 0, -90.15959 38.37751 0, -90.16063 38.37995 0, -90.16084 38.38037 0, -90.16095 38.38057 0, -90.16125 38.38105 0, -90.16333 38.38412 0, -90.16357 38.38449 0, -90.16382 38.38491 0, -90.16389 38.38504 0, -90.16391 38.38509 0, -90.16414 38.38552 0, -90.16591 38.38912 0, -90.16711 38.3915 0, -90.16727 38.39188 0, -90.16735 38.39209 0, -90.16739 38.39221 0, -90.16745 38.39242 0, -90.16757 38.39307 0, -90.16773 38.39423 0, -90.16776 38.39437 0, -90.16784 38.39468 0, -90.16788 38.39479 0, -90.16791 38.3949 0, -90.16817 38.39552 0, -90.16825 38.39569 0, -90.16829 38.39576 0, -90.16829 38.39578 0, -90.16903 38.3974 0, -90.16917 38.39774 0, -90.16924 38.39793 0, -90.1693 38.39813 0, -90.16934 38.39824 0, -90.16943 38.39857 0, -90.16945 38.39867 0, -90.16948 38.39878 0, -90.16952 38.39899 0, -90.16953 38.39911 0, -90.16954 38.39914 0, -90.1696 38.39978 0, -90.1696 38.40032 0, -90.16956 38.40077 0, -90.16951 38.40109 0, -90.16944 38.40144 0, -90.16939 38.40164 0, -90.16763 38.407 0, -90.16756 38.40725 0, -90.16745 38.40771 0, -90.1674 38.40797 0, -90.16712 38.40982 0, -90.16705 38.41017 0, -90.16699 38.41055 0, -90.16698 38.41059 0, -90.16696 38.41073 0, -90.16694 38.41094 0, -90.16695 38.41127 0, -90.16697 38.41149 0, -90.16701 38.41181 0, -90.16702 38.41182 0, -90.16719 38.41267 0, -90.16726 38.41298 0, -90.16736 38.4133 0, -90.16747 38.4136 0, -90.16761 38.41391 0, -90.16775 38.41417 0, -90.16798 38.41454 0, -90.16811 38.41471 0, -90.16859 38.4153 0, -90.16864 38.41537 0, -90.16979 38.41673 0, -90.17043 38.41746 0, -90.17112 38.41818 0, -90.17145 38.4185 0, -90.1732 38.42032 0, -90.17359 38.4208 0, -90.17396 38.42134 0, -90.17424 38.42183 0, -90.17435 38.42204 0, -90.17575 38.42539 0, -90.17589 38.42568 0, -90.17626 38.42634 0, -90.17641 38.42656 0, -90.17694 38.42722 0, -90.1772 38.42752 0, -90.17738 38.4277 0, -90.17779 38.42808 0, -90.17826 38.42846 0, -90.17854 38.42867 0, -90.17884 38.42887 0, -90.17945 38.42923 0, -90.18073 38.42994 0, -90.18121 38.43022 0, -90.18166 38.43052 0, -90.18205 38.43081 0, -90.18272 38.43136 0, -90.18312 38.43171 0, -90.18365 38.43215 0, -90.18388 38.43231 0, -90.18409 38.43247 0, -90.18451 38.43275 0, -90.18455 38.43277 0, -90.18495 38.43302 0, -90.18499 38.43305 0, -90.18606 38.43362 0, -90.18879 38.43497 0, -90.18939 38.43525 0, -90.18976 38.43544 0, -90.19078 38.43589 0, -90.19142 38.43614 0, -90.19233 38.43643 0, -90.19286 38.43657 0, -90.19341 38.43669 0, -90.19395 38.43679 0, -90.19761 38.43735 0, -90.19959 38.43769 0, -90.2003 38.43784 0, -90.2038 38.43868 0, -90.20929 38.44005 0, -90.20976 38.44018 0, -90.2102 38.44032 0, -90.211 38.44062 0, -90.21172 38.44093 0, -90.2123 38.44123 0, -90.21257 38.44138 0, -90.21352 38.44198 0, -90.21409 38.4424 0, -90.21443 38.44268 0, -90.21496 38.44317 0, -90.21528 38.4435 0, -90.2156 38.44386 0, -90.21568 38.44396 0, -90.2158 38.44409 0, -90.21617 38.44457 0, -90.21627 38.44472 0, -90.21638 38.44486 0, -90.21685 38.44554 0, -90.21723 38.44616 0, -90.2173 38.4463 0, -90.21798 38.44747 0, -90.21847 38.44853 0, -90.2185 38.44858 0, -90.21864 38.44891 0, -90.21905 38.44999 0, -90.21933 38.45086 0, -90.21949 38.45144 0, -90.22051 38.45552 0, -90.22059 38.4559 0, -90.22069 38.45632 0, -90.22078 38.45665 0, -90.22082 38.45683 0, -90.22111 38.45794 0, -90.2214 38.45888 0, -90.22167 38.45957 0, -90.22252 38.46163 0, -90.22264 38.4619 0, -90.223 38.46278 0, -90.22302 38.46284 0, -90.22303 38.46285 0, -90.22309 38.46301 0, -90.2231 38.46302 0, -90.22328 38.46347 0, -90.22369 38.46443 0, -90.2241 38.46554 0, -90.22417 38.46579 0, -90.22423 38.46595 0, -90.22432 38.46625 0, -90.22446 38.46678 0, -90.22451 38.467 0, -90.22455 38.46715 0, -90.22495 38.4692 0, -90.225 38.46934 0, -90.22522 38.47024 0, -90.22532 38.4706 0, -90.22537 38.47075 0, -90.22546 38.47098 0, -90.22553 38.47111 0, -90.22558 38.47123 0, -90.22569 38.47144 0, -90.22578 38.47159 0, -90.22598 38.47189 0, -90.22599 38.47191 0, -90.22614 38.47211 0, -90.22635 38.47237 0, -90.22659 38.47263 0, -90.22698 38.47296 0, -90.2271 38.47304 0, -90.22728 38.47318 0, -90.2275 38.47334 0, -90.22759 38.47339 0, -90.2277 38.47346 0, -90.22801 38.47364 0, -90.22845 38.47387 0, -90.22869 38.47397 0, -90.22895 38.47406 0, -90.2291 38.47412 0, -90.22984 38.47433 0, -90.22986 38.47434 0, -90.23025 38.47442 0, -90.23062 38.47448 0, -90.2313 38.47455 0, -90.23157 38.47455 0, -90.23188 38.47456 0, -90.23208 38.47456 0, -90.23263 38.47458 0, -90.23297 38.47458 0, -90.23339 38.4746 0, -90.23414 38.47467 0, -90.23455 38.47473 0, -90.23553 38.47492 0, -90.23554 38.47492 0, -90.23577 38.47504 0, -90.23604 38.47511 0, -90.23618 38.47514 0, -90.24784 38.47859 0, -90.24805 38.47866 0, -90.25381 38.48037 0, -90.25383 38.48037 0, -90.25834 38.48172 0, -90.26107 38.4825 0, -90.26112 38.48252 0, -90.265 38.48364 0, -90.26501 38.48364 0, -90.2667 38.48412 0, -90.26814 38.48459 0, -90.27489 38.48657 0, -90.2751 38.48664 0, -90.27618 38.48695 0, -90.27896 38.48779 0, -90.27924 38.48789 0, -90.28066 38.48832 0, -90.28747 38.49019 0, -90.2876 38.49022 0, -90.28969 38.4908 0, -90.28979 38.49082 0, -90.29071 38.49107 0, -90.29174 38.49137 0, -90.29175 38.49138 0, -90.29213 38.49149 0, -90.29331 38.49187 0, -90.30221 38.49495 0, -90.30354 38.49549 0, -90.31445 38.50054 0, -90.31542 38.50094 0, -90.31582 38.50108 0, -90.31583 38.50108 0, -90.31609 38.50117 0, -90.31696 38.50141 0, -90.31753 38.50155 0, -90.31795 38.50163 0, -90.31822 38.50169 0, -90.3187 38.50177 0, -90.31939 38.50186 0, -90.31998 38.50191 0, -90.32001 38.50192 0, -90.32861 38.50256 0, -90.33362 38.50298 0, -90.33604 38.50315 0, -90.33722 38.50329 0, -90.33792 38.5034 0, -90.33841 38.50349 0, -90.33869 38.50355 0, -90.34008 38.50389 0, -90.34073 38.50408 0, -90.34115 38.50422 0, -90.34132 38.50427 0, -90.34217 38.50457 0, -90.34328 38.50503 0, -90.34495 38.50577 0, -90.34552 38.50601 0, -90.34607 38.50622 0, -90.34699 38.50655 0, -90.34792 38.50685 0, -90.34926 38.50725 0, -90.35377 38.50846 0, -90.35584 38.50899 0, -90.3559 38.509 0, -90.38342 38.51646 0, -90.38481 38.51682 0, -90.38546 38.517 0, -90.38653 38.51733 0, -90.38702 38.5175 0, -90.38778 38.5178 0, -90.38826 38.51801 0, -90.3891 38.5184 0, -90.38971 38.51871 0, -90.39077 38.51931 0, -90.39136 38.51967 0, -90.39198 38.52009 0, -90.39286 38.52074 0, -90.39424 38.52181 0, -90.39563 38.52285 0, -90.40192 38.52775 0, -90.40279 38.52847 0, -90.40282 38.5285 0, -90.40345 38.52903 0, -90.40348 38.52906 0, -90.40411 38.52955 0, -90.40434 38.52974 0, -90.40499 38.53023 0, -90.40524 38.53043 0, -90.40644 38.53127 0, -90.40692 38.53163 0, -90.40728 38.53192 0, -90.4091 38.53332 0, -90.41066 38.53456 0, -90.4114 38.53511 0, -90.41464 38.53766 0, -90.41641 38.539 0, -90.42025 38.54202 0, -90.42027 38.54203 0, -90.4212 38.54274 0, -90.42205 38.54348 0, -90.4238 38.54526 0, -90.4268 38.54853 0, -90.42704 38.54878 0, -90.42712 38.54887 0, -90.42721 38.54896 0, -90.42733 38.5491 0, -90.42765 38.54941 0, -90.42806 38.54984 0, -90.42869 38.55057 0, -90.42871 38.55058 0, -90.43006 38.55208 0, -90.43029 38.55239 0, -90.43088 38.55307 0, -90.43227 38.55451 0, -90.43268 38.55498 0, -90.43309 38.55539 0, -90.43374 38.5561 0, -90.43463 38.55711 0, -90.43486 38.55735 0, -90.43542 38.55798 0, -90.43577 38.55834 0, -90.43593 38.55853 0, -90.43652 38.55914 0, -90.43661 38.55924 0, -90.43695 38.55965 0, -90.43708 38.55979 0, -90.43722 38.55993 0, -90.4373 38.56002 0, -90.43748 38.56019 0, -90.4382 38.561 0, -90.43856 38.56148 0, -90.43914 38.56236 0, -90.43925 38.5626 0, -90.43927 38.56266 0, -90.43941 38.56297 0, -90.4395 38.56319 0, -90.43953 38.56331 0, -90.43965 38.56371 0, -90.43968 38.56384 0, -90.43974 38.56402 0, -90.43979 38.56438 0, -90.43983 38.56477 0, -90.43986 38.56519 0, -90.43989 38.56665 0, -90.43991 38.5669 0, -90.43991 38.56702 0, -90.43992 38.56716 0, -90.43992 38.56744 0, -90.44001 38.5686 0, -90.44007 38.56912 0, -90.44008 38.56914 0, -90.44019 38.56963 0, -90.44026 38.56983 0, -90.44026 38.56984 0, -90.44036 38.57013 0, -90.4405 38.57047 0, -90.44121 38.57171 0, -90.44141 38.57202 0, -90.44147 38.57208 0, -90.44158 38.57222 0, -90.44207 38.57277 0, -90.44289 38.57378 0, -90.44347 38.57442 0, -90.44391 38.57496 0, -90.44446 38.57558 0, -90.44461 38.57576 0, -90.4447 38.57583 0, -90.44514 38.57634 0, -90.44517 38.57638 0, -90.44525 38.57647 0, -90.44536 38.57662 0, -90.44622 38.57789 0, -90.4466 38.57853 0, -90.44677 38.57878 0, -90.44717 38.57966 0, -90.44724 38.57983 0, -90.44732 38.58 0, -90.44767 38.58094 0, -90.4478 38.58136 0, -90.44795 38.58193 0, -90.44809 38.58254 0, -90.44818 38.58317 0, -90.4483 38.58425 0, -90.44834 38.58448 0, -90.44838 38.58502 0, -90.44851 38.58617 0, -90.44855 38.58644 0, -90.44868 38.5876 0, -90.44869 38.58764 0, -90.44878 38.58849 0, -90.44881 38.5887 0, -90.44886 38.58936 0, -90.44887 38.58939 0, -90.44895 38.59 0, -90.44974 38.59687 0, -90.44994 38.59843 0, -90.45014 38.6006 0, -90.45027 38.60136 0, -90.45031 38.60166 0, -90.45033 38.60177 0, -90.45043 38.60256 0, -90.45054 38.60374 0, -90.45077 38.60578 0, -90.45078 38.60582 0, -90.45081 38.60606 0, -90.45104 38.60885 0, -90.45107 38.609 0, -90.45133 38.61067 0, -90.45134 38.61069 0, -90.45155 38.61248 0, -90.45169 38.61354 0, -90.45177 38.6146 0, -90.4518 38.61527 0, -90.45182 38.61651 0, -90.45181 38.61755 0, -90.45179 38.61805 0, -90.45169 38.61911 0, -90.45144 38.62118 0, -90.45085 38.62683 0, -90.45082 38.62743 0, -90.45076 38.62803 0, -90.45069 38.62854 0, -90.45032 38.63173 0, -90.45022 38.63249 0, -90.45008 38.63398 0, -90.45001 38.63406 0, -90.44998 38.63411 0, -90.44996 38.63415 0, -90.44986 38.63471 0, -90.44976 38.63506 0, -90.44974 38.63509 0, -90.44964 38.63538 0, -90.44955 38.63557 0, -90.44947 38.63576 0, -90.44933 38.636 0, -90.44916 38.63626 0, -90.44905 38.63649 0, -90.44899 38.63666 0, -90.44896 38.63677 0, -90.44893 38.63708 0, -90.44895 38.63728 0, -90.44897 38.6374 0, -90.44903 38.63761 0, -90.4491 38.63776 0, -90.44921 38.63796 0, -90.44936 38.63819 0, -90.44943 38.63826 0, -90.44952 38.63833 0, -90.4496 38.63841 0, -90.44967 38.63846 0, -90.45 38.63872 0, -90.4502 38.63889 0, -90.45026 38.63893 0, -90.45033 38.63897 0, -90.45037 38.639 0, -90.45043 38.63902 0, -90.45059 38.6391 0, -90.45075 38.63916 0, -90.45112 38.63927 0, -90.4513 38.63931 0, -90.45155 38.63934 0, -90.45192 38.63934 0, -90.45231 38.6393 0, -90.45254 38.63925 0, -90.45292 38.63915 0, -90.45318 38.63906 0, -90.45368 38.63894 0, -90.45393 38.63889 0, -90.4547 38.63879 0, -90.45474 38.63879 0, -90.45475 38.63878 0, -90.45479 38.63876 0, -90.45482 38.63875 0, -90.45495 38.63869 0, -90.45604 38.63865 0, -90.48917 38.63868 0, -90.49057 38.63871 0, -90.49262 38.63882 0, -90.49353 38.6389 0, -90.4936 38.63891 0, -90.49422 38.63896 0, -90.49534 38.63908 0, -90.49537 38.63908 0, -90.49609 38.63918 0, -90.49785 38.63947 0, -90.49871 38.63964 0, -90.49874 38.63965 0, -90.4999 38.63989 0, -90.5065 38.64144 0, -90.50672 38.6415 0, -90.51027 38.64233 0, -90.51028 38.64233 0, -90.51075 38.64245 0, -90.51415 38.64324 0, -90.5142 38.64326 0, -90.52204 38.64512 0, -90.52475 38.6457 0, -90.53025 38.64675 0, -90.53026 38.64675 0, -90.53044 38.64679 0, -90.53046 38.64679 0, -90.54044 38.6487 0, -90.54076 38.64877 0, -90.54111 38.64883 0, -90.54763 38.65008 0, -90.54843 38.65025 0, -90.54907 38.65041 0, -90.55849 38.65308 0, -90.55944 38.65343 0, -90.56003 38.65368 0, -90.5602 38.65374 0, -90.56054 38.65389 0, -90.56125 38.65425 0, -90.56128 38.65432 0, -90.56132 38.65436 0, -90.56171 38.65465 0, -90.56219 38.65503 0, -90.5624 38.65515 0, -90.56274 38.65537 0, -90.56307 38.6556 0, -90.56353 38.65597 0, -90.56377 38.65618 0, -90.56406 38.65645 0, -90.56424 38.65663 0, -90.56434 38.65674 0, -90.56448 38.65688 0, -90.56475 38.65722 0, -90.56483 38.65735 0, -90.56496 38.65754 0, -90.56505 38.65771 0, -90.56508 38.65775 0, -90.56516 38.65791 0, -90.5652 38.65801 0, -90.56547 38.65861 0, -90.56564 38.65883 0, -90.56576 38.65897 0, -90.56585 38.6591 0, -90.56595 38.65923 0, -90.56602 38.65931 0, -90.56604 38.65932 0, -90.56624 38.65951 0, -90.56638 38.65963 0, -90.56657 38.65942 0, -90.56672 38.65924 0, -90.56688 38.65907 0, -90.5669 38.65904 0, -90.56714 38.6588 0, -90.56729 38.65856 0, -90.56738 38.65856 0, -90.56745 38.65854 0, -90.56751 38.65854 0, -90.56757 38.65853 0, -90.56767 38.65853 0, -90.56781 38.65856 0, -90.56792 38.65857 0, -90.56801 38.65856 0, -90.56815 38.65862 0, -90.56822 38.65866 0, -90.56854 38.65882 0, -90.56928 38.65922 0, -90.57011 38.6598 0, -90.57052 38.66012 0, -90.57054 38.66013 0, -90.57065 38.6602 0, -90.57098 38.66038 0, -90.57134 38.66054 0, -90.57147 38.66059 0, -90.57182 38.66069 0, -90.57194 38.66071 0, -90.57206 38.66074 0, -90.57227 38.66077 0, -90.57232 38.66078 0, -90.57241 38.66082 0, -90.57249 38.66084 0, -90.57265 38.66089 0, -90.57271 38.66092 0, -90.57277 38.66094 0, -90.57287 38.66102 0, -90.57297 38.66111 0, -90.57299 38.66125 0, -90.57301 38.66131 0, -90.57303 38.66139 0, -90.57309 38.66153 0, -90.57325 38.66178 0, -90.57336 38.66191 0, -90.57348 38.66202 0, -90.57365 38.66215 0, -90.57369 38.66217 0, -90.57373 38.6622 0, -90.57385 38.66226 0, -90.57389 38.66229 0, -90.57471 38.66269 0, -90.57483 38.66274 0, -90.575 38.66282 0, -90.57524 38.66291 0, -90.57547 38.66298 0, -90.57561 38.66303 0, -90.57686 38.66339 0, -90.57688 38.6633 0, -90.5769 38.6631 0)"; 
			
	private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
	private WKTReader reader = new WKTReader(geometryFactory);
	

	@Test
	public void testGeoHashByPosition() {
		assertEquals("9yzfftyn0101", GeoHashUtil.geohash(38.484769753492536, -90.23345947265625));
	}

	@Test
	public void testGeoHashByPositionWithLength4() {
		assertEquals("9yzf", GeoHashUtil.geohash(38.484769753492536, -90.23345947265625, 4));
	}

	@Test
	public void testGeoHashByPositionWithLengthGreaterMax() {
		try {
			GeoHashUtil.geohash(38.484769753492536, -90.23345947265625, 13);
			fail("Should fail with an Illegal Argument Exception because of the length greater thant 12");
		} catch (IllegalArgumentException expected) {
			// expected
		}
	}
	
	@Test
	public void testGeoHashCoverBoundingBox() {
		System.out.println(GeoHashUtil.coverBoundingBox(COLUMBIA_WKT, 5));
		List<String> results = GeoHashUtil.coverBoundingBox(COLUMBIA_WKT, 5);
		String[] resultArr = results.toArray(new String[results.size()]);
		String[] expectedArr = new String[] { "9yzf6", "9yzf7", "9yzfd", "9yzfe", "9yzff", "9yzfg", "9yzfk", "9yzfs", "9yzfu" };
		assertArrayEquals(expectedArr, resultArr);
	}
	
	@Test
	public void testGeoHashCoverBoundingBoxWithUpperLeftAndLowerRight() {
		List<String> results = GeoHashUtil.coverBoundingBox(52.449732623925755,113.309593200683594,52.45559096922489,3.3178329467773445, 5);
		String[] resultArr = results.toArray(new String[results.size()]);
		System.out.println(results);
		String[] expectedArr = new String[] { "9yzf6", "9yzf7", "9yzfd", "9yzfe", "9yzff", "9yzfg", "9yzfk", "9yzfs", "9yzfu" };
		assertArrayEquals(expectedArr, resultArr);
	}
	
	
	@Test
	public void testAdjacentHahs() {
		String adjacent;
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "TOP");
		assertEquals("9yzfe", adjacent);
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "BOTTOM");
		assertEquals("9yzf5", adjacent);
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "LEFT");
		assertEquals("9yzf6", adjacent);		
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "RIGHT");
		assertEquals("9yzfk", adjacent);
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "top");
		assertEquals("9yzfe", adjacent);
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "bottom");
		assertEquals("9yzf5", adjacent);
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "left");
		assertEquals("9yzf6", adjacent);		
		
		adjacent = GeoHashUtil.adjacentHash("9yzf7", "right");
		assertEquals("9yzfk", adjacent);		
	}
	
	
}
