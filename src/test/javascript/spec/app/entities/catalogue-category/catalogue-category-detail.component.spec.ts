import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CatalogueCategoryDetailComponent } from 'app/entities/catalogue-category/catalogue-category-detail.component';
import { CatalogueCategory } from 'app/shared/model/catalogue-category.model';

describe('Component Tests', () => {
  describe('CatalogueCategory Management Detail Component', () => {
    let comp: CatalogueCategoryDetailComponent;
    let fixture: ComponentFixture<CatalogueCategoryDetailComponent>;
    const route = ({ data: of({ catalogueCategory: new CatalogueCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CatalogueCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatalogueCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatalogueCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catalogueCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catalogueCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
