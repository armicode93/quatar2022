package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.service.*;
import com.example.qatar2022.service.personne.JoueurService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/v1/partie")
@CrossOrigin(origins = "*")
public class PartieController {

    private final PartieService partieService;
    private final ImageService imageService;
    private final EquipeService equipeService;
    private final StadeService stadeService;
    private final TourService tourService;


    private final JoueurService  joueurService;

    public PartieController(PartieService partieService, ImageService imageService, EquipeService equipeService, StadeService stadeService, TourService tourService, JoueurService joueurService) {
        this.partieService = partieService;
        this.imageService = imageService;
        this.equipeService = equipeService;
        this.stadeService = stadeService;
        this.tourService = tourService;
        this.joueurService = joueurService;
    }


    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int
            page, @RequestParam(defaultValue = "0") int size,  @RequestParam(defaultValue = "title") String sortBy)
    {

        // <Partie> parties = partieService.getPartiteByTurno();
        List <Image> images = imageService.getAllImage();
     //   List<Tour> tours = tourService.getallTour();

        List<Partie> parties = partieService.getAllPartie();

       /* Map<Tour, List<P
       artie>> partitePerTurno = new LinkedHashMap<>(); // mappa per associare ad ogni turno l'elenco delle partite
        for (Tour tour : tours) {
            List<Partie> partite = partieService.getPartiteByTurno(tour); // recupera le partite del turno corrente dal database

            partitePerTurno.put(tour, partite); // aggiunge il turno e l'elenco delle partite alla mappa
        }

        */
        //model.addAttribute("partitePerTurno", partitePerTurno);

        model.addAttribute("parties",parties);
       // model.addAttribute("tours", tours);
        model.addAttribute("images", images);
        model.addAttribute("title","Liste des matches");
        // recupera tutti i turni

        return "index";

    }










    @GetMapping("/show/{idPartie}")
    public String show (Model model, @PathVariable(name="idPartie")Long idPartie)
    {

         Partie partie = partieService.getPartieById(idPartie);

         model.addAttribute("partie",partie);

         /*model.addAttribute("image1",partie.getEq1().getDrapeau().getId());
         model.addAttribute("image2", partie.getEq2().getDrapeau().getId());

          */



        model.addAttribute("title","Detail Partie");

         return "partie/show";
    }




    @GetMapping("/add")
    public String partieFormAdd(Model model)
    {
        List<Equipe> equipes = equipeService.getAllEquipe();
        List<Stade> stades = stadeService.getAllStade();
        List<Tour> tours= tourService.getallTour();

        model.addAttribute("stades",stades);
        model.addAttribute("equipes",equipes);
        model.addAttribute("tours",tours);
        model.addAttribute(new Partie());


        return "partie/add";
    }
    /*
    @GetMapping("/showJoueurByPartie/{idPartie}")
    public String showJoueurByPartie (Model model, @PathVariable(name="idPartie")Long idPartie)
    {


        Partie partie =  partieService.getPartieById(idPartie);





        model.addAttribute("partie",partie);
        model.addAttribute("joueurs", joueurService.getAllJoueur());






        return "partie/showJoueurByPartie";
    }

     */




    @PostMapping("/add")
    public String partieSubmitAdd( @ModelAttribute("partie") Partie partie,@ModelAttribute("equipe") Equipe equipe,@ModelAttribute("stade") Stade stade,@ModelAttribute("tour") Tour tour, BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            return "partie/add";
        }
        partie.setEq1(partie.getEq1());
        partie.setEq2(partie.getEq2());
        partie.setStade(partie.getStade());

        partie.setTour(partie.getTour());
       //partie.setScoreEq1(partie.getScoreEq1());
        //partie.setScoreEq2(partie.getScoreEq2());




        partie.setDateTime(partie.getDateTime());
        partie.setArbitre_principal(partie.getArbitre_principal());
        partie.setTotalTime(partie.getTotalTime());
        partie.setProlongation(partie.getProlongation());
        partie.setPrix(partie.getPrix());





        partieService.addPartie(partie);

        model.addAttribute("eq1",equipe.getIdEquipe());
        model.addAttribute("eq2",equipe.getIdEquipe());
        model.addAttribute("stade",stade.getIdStade());
        model.addAttribute("tour",tour.getIdTour());

        model.addAttribute("partie","");



        return "redirect:/";


    }




        @DeleteMapping(path ="{idPartie}")
        public void deletePartie(@PathVariable("idPartie") Long idPartie)

        {
           partieService.deletePartie(idPartie);
        }

   /* @PutMapping(path = "/" )
    public ResponseEntity updatePartie (@RequestBody Partie partieBody) {


        if (partieBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Partie> partie = Optional.ofNullable(partieService.getPartieById(partieBody.getIdPartie()));

        if (partie.isPresent()) {
            Partie createPartie = partieService.updatePartie(partieBody.getIdPartie(), partieBody);
            return ResponseEntity.ok(partieBody);
        }
        return ResponseEntity.notFound().build();
    }

    */

    @GetMapping("/editResult/{idPartie}")
    public String editResult (Model model, @PathVariable(name="idPartie")String idPartie)
    {
        Partie partie = partieService.getPartieByIdPartie(idPartie);

        model.addAttribute("partie",partie);
        model.addAttribute("title","");

        return "partie/editResultForm";


    }

    @PostMapping ("/editResult/{idPartie}")
    public String editResultSubmit (@Valid @ModelAttribute("partie") Partie partie,@RequestParam("scoreEq1") int scoreEq1,
                                    @RequestParam("scoreEq2") int scoreEq2,BindingResult result, @PathVariable("idPartie") Long idPartie, Model model)
    {

        if (result.hasErrors())
        {
            return "partie/editResultForm";
        }
         Partie existing = partieService.getPartieById(idPartie);


        if (existing == null)
        {
            return "redirect:/";
        }


        partieService.updatePartie(idPartie, scoreEq1, scoreEq2);



        return "redirect:/";


    }
}
