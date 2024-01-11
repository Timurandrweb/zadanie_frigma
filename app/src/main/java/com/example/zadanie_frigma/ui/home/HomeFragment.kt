package com.example.zadanie_frigma.ui.home

import android.R
import android.graphics.PorterDuff
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.zadanie_frigma.databinding.FragmentHomeBinding
import com.example.zadanie_frigma.poluchenie_json.pol_json
import com.example.zadanie_frigma.room_sql.MainDb
import com.example.zadanie_frigma.ui.home.RecyclerViews.Language
import com.example.zadanie_frigma.ui.home.RecyclerViews.RvAdapter
import com.example.zadanie_frigma.ui.home.offline.MViewModel_insert
import com.example.zadanie_frigma.ui.home.offline.MainFactory2
import com.example.zadanie_frigma.ui.home.viewlogika_region_list.MainFactory
import com.example.zadanie_frigma.ui.home.viewlogika_region_list.MainViewModel
import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.logging.Handler
import kotlin.concurrent.thread


class HomeFragment : Fragment() {
    lateinit var mViewModel: MainViewModel
    lateinit var mViewModel_insert: MViewModel_insert

    // get reference to the adapter class
    private var languageList = ArrayList<Language>()
    private lateinit var rvAdapter: RvAdapter


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    var scrollpizza:Int=0//положение по горизонтали Х(меню с выбором пиццы пицца, комбо, десерты...)
    val pol_json = pol_json()
    lateinit var observer_json: Observer<String>
    var t = false
    var handler: Handler? = null


    var zapusk:Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.zagruz.visibility = View.VISIBLE
        //RecyclerView лента для пиццы
        binding.rvList.setNestedScrollingEnabled(false);
        binding.rvList.setHasFixedSize(false);
        binding.rvList.setNestedScrollingEnabled(false);
        binding.rvList.setLayoutManager(LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false))
        rvAdapter = RvAdapter(languageList)
        binding.rvList.adapter = rvAdapter
        //RecyclerView



        //создания списка городов
        mViewModel = ViewModelProvider(this, MainFactory(requireActivity().getApplication(),"Передаем текст например геолокацю")).get(MainViewModel::class.java) //c аргументом
        mViewModel.liveData.observe(requireActivity(), Observer {
            binding.spinner.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            binding.spinner.setAdapter(it)
        })



        //анимация с панелькой (пицца, комбо, десерты...
        osnovn_scroll()//при скролинге фиксирует или открепляет панель
        scroll_menu()//координирует общее положение панели по КООРДИНАТЕ: X









        pol_json.sendServer()//запрос json (ТУТ пиццы)
        observer_json = Observer {
            //данные возвращаются!
            //it - данные json с сервера

            //надо добавить в бд если соединение стабильно
            if (!it.toString().equals("error")){
                mViewModel_insert = ViewModelProvider(this, MainFactory2(requireActivity().getApplication(),it.toString())).get(MViewModel_insert::class.java)
            }else{
                Toast.makeText(requireContext(),"НЕТ СЕТИ",Toast.LENGTH_LONG).show()
            }

            if (zapusk == false){
                zapusk = true
                runBlocking {
                    launch {
                        delay(5000L)
                        val db = MainDb.getDb(requireContext())
                        db.getDao().getAllItem().asLiveData().observe(requireActivity()){ list->
                            list.forEach {
                                var language1 = Language(
                                    "${it.name}",
                                    "${it.img}",
                                    "В api не было описания! Ветчина, шампиньоны, увеличенная порция моцареллы, томатный соус",
                                    "от ${it.price}р"
                                )
                                languageList.add(language1)
                                rvAdapter.notifyDataSetChanged()
                            }
                        }
                        binding.zagruz.visibility = View.GONE
                    }

                }
            }else{
                binding.zagruz.visibility = View.GONE
            }


        }



        return root
    }







    override fun onStart() {
        super.onStart()
        pol_json.observe(this,observer_json)
    }

    override fun onStop() {
        super.onStop()
        pol_json.removeObserver(observer_json)
    }





    //АНИМАЦИЯ координирует общее положение 2ух панелей по КООРДИНАТЕ: X (пицца, комбо, десерты...
    @RequiresApi(Build.VERSION_CODES.M)
    private fun scroll_menu() {
        //объединяет scroll pizza
        binding.pizzascroll1.setOnScrollChangeListener(View.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            try {
                binding.pizzascroll2.post(Runnable { binding.pizzascroll2.scrollTo(scrollX, 0) })
            } catch (e: Exception) {
                e.cause
            }
        })

        binding.pizzascroll2.setOnScrollChangeListener(View.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            try {
                scrollpizza = scrollX;
            } catch (e: Exception) {
                e.cause
            }
        })
        //объединяет scroll pizza
    }

    //АНИМАЦИЯ при скролинге фиксирует или открепляет панель (пицца, комбо, десерты...
    @RequiresApi(Build.VERSION_CODES.M)
    private fun osnovn_scroll() {
        //При основном скролле
        binding.scroll.setOnScrollChangeListener(View.OnScrollChangeListener { view, i, i1, i2, i3 ->
            var boolean:Boolean = false
            val scrollBounds = Rect()
            binding.scroll.getHitRect(scrollBounds)
            if (binding.listtt.getLocalVisibleRect(scrollBounds)) {
                // Любая часть изображения, даже один пиксель, находится в пределах видимого окна
                binding.listtt2.visibility =  View.GONE
                if (boolean == false){
                    binding.pizzascroll1.post(Runnable { binding.pizzascroll1.scrollTo(scrollpizza, 0) })
                }
                boolean = false
            } else {
                // НИ одно изображение не находится в пределах видимого окна
                binding.listtt2.visibility =  View.VISIBLE
                boolean = true
            }
        })
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

